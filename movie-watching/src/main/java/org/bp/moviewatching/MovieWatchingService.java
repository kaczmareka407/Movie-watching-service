package org.bp.moviewatching;

import static org.apache.camel.model.rest.RestParamType.body;


import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.SagaPropagation;
import org.apache.camel.model.rest.RestBindingMode;
import org.bp.moviewatching.model.MovieWatchingRequest;
import org.bp.moviewatching.model.Utils;
import org.bp.payment.PaymentRequest;
import org.apache.camel.converter.jaxb.JaxbDataFormat;
import org.springframework.stereotype.Component;
import org.apache.camel.*;

@Component
public class MovieWatchingService extends RouteBuilder {
    @org.springframework.beans.factory.annotation.Autowired
    BookingIdentifierService bookingIdentifierService;
    String movieBookingServiceServer = "localhost:8080";
    String accountSubscriptionServiceServer = "localhost:8081";
    String paymentServiceServer = "localhost:8083";


    @Override
    public void configure() throws Exception {
        gateway();

    }


    public void gateway() {


        restConfiguration()
                .component("servlet")
                .bindingMode(RestBindingMode.json)
                .dataFormatProperty("prettyPrint", "true")
                .enableCORS(true)
                .contextPath("/api")
                // turn on swagger api-doc
                .apiContextPath("/api-doc")
                .apiProperty("api.title", "Movie watching API")
                .apiProperty("api.version", "1.0.0");

        rest("/moviewatching").description("Movie watching REST service")
                .consumes("application/json")
                .produces("application/json")
                .post("/watching").description("Watch movie").type(MovieWatchingRequest.class).outType(org.bp.payment.PaymentResponse.class)
                .param().name("body").type(body).description("Watch movie").endParam()
                .responseMessage().code(200).message("You can watch the movie!").endResponseMessage()
                .to("direct:moviewatching")
                .get("/watching/payment/{id}")
                .to("direct:bookingPayment");

        from("direct:moviewatching").routeId("moviewatching")
                .log("moviewatching fired")
                .process((exchange) -> {
                    exchange.setProperty("paymentRequest",
                            Utils.preparePaymentRequest(exchange.getMessage().getBody(MovieWatchingRequest.class)));
                    exchange.setProperty("watchingBookingId", bookingIdentifierService.generateBookingId());
                })
                .saga()
                .multicast()
                .parallelProcessing()
                .aggregationStrategy((prevEx, currentEx) -> {
                    if (currentEx.getException() != null)
                        return currentEx;
                    if (prevEx != null && prevEx.getException() != null)
                        return prevEx;

                    org.bp.payment.PaymentRequest paymentRequest;
                    if (prevEx == null)
                        paymentRequest = currentEx.getProperty("paymentRequest", org.bp.payment.PaymentRequest.class);
                    else
                        paymentRequest = prevEx.getMessage().getBody(org.bp.payment.PaymentRequest.class);

                    Object body = currentEx.getMessage().getBody();
                    int cost;
                    if (body instanceof org.bp.BookingInfo)
                        cost = ((org.bp.BookingInfo) body).getCost().intValue();
                    else if (body instanceof org.bp.accountsubscribing.BookMovieSubscriptionResponse)
                        cost = ((org.bp.accountsubscribing.BookMovieSubscriptionResponse) body).getReturn().getCost();
                    else
                        return prevEx;

                    paymentRequest.setAmount(paymentRequest.getAmount() + cost);
                    currentEx.getMessage().setBody(paymentRequest);

                    return currentEx;
                })
                .to("direct:bookAccountSubscription")
                .to("direct:bookMovie")
                .end()
                .process((currentEx) ->
                {
                    currentEx.getMessage().setBody(
                            currentEx.getProperty("paymentRequest", org.bp.payment.PaymentRequest.class));
                })
                .to("direct:payment")
                .removeHeaders("Camel*")
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(200));


        final JaxbDataFormat jaxbMovie = new JaxbDataFormat(org.bp.BookingInfo.class.getPackage().getName());

        from("direct:bookMovie").routeId("bookMovie")
                .log("bookMovie fired")
                .saga()
                .propagation(SagaPropagation.MANDATORY)
                .compensation("direct:cancelMovie").option("watchingBookingId", simple("${exchangeProperty.watchingBookingId}"))
                .process((exchange) ->
                {
                    exchange.getMessage().setBody(
                            Utils.prepareMovieBookingRequest(exchange.getMessage().getBody(MovieWatchingRequest.class)));
                })
                .marshal(jaxbMovie)
                .to("spring-ws:http://" + movieBookingServiceServer + "/soap-api/service/movie")
                .to("stream:out")
                .unmarshal(jaxbMovie)
                .process((exchange) -> {
                    org.bp.BookingInfo bookMovieResponse = exchange.getMessage().getBody(org.bp.BookingInfo.class);
                    String watchingBookingId = exchange.getProperty("watchingBookingId", String.class);
                    bookingIdentifierService.assignMovieBookingId(watchingBookingId, bookMovieResponse.getId());

                    exchange.getProperty("paymentRequest", PaymentRequest.class).setWatchingId(watchingBookingId);
                    exchange.getProperty("paymentRequest", PaymentRequest.class).setMovieBookingId(bookingIdentifierService.getMovieBookingId(watchingBookingId));
                });

        from("direct:cancelMovie").routeId("cancelMovie")
                .log("cancelMovie fired")
                .process((exchange) -> {
                    String watchingId = exchange.getMessage().getHeader("watchingBookingId", String.class);
                    int movieBookingId = bookingIdentifierService.getMovieBookingId(watchingId);
                    org.bp.CancelBookingRequest cancelMovieBookingRequest = new org.bp.CancelBookingRequest();
                    cancelMovieBookingRequest.setBookingId(movieBookingId);
                    exchange.getMessage().setBody(cancelMovieBookingRequest);
                })
                .marshal(jaxbMovie)
                .to("spring-ws:http://" + movieBookingServiceServer + "/soap-api/service/movie")
                .to("stream:out")
                .unmarshal(jaxbMovie);

        final JaxbDataFormat jaxbAccountSubscription = new JaxbDataFormat(org.bp.accountsubscribing.BookMovieSubscriptionResponse.class.getPackage().getName());


        from("direct:bookAccountSubscription").routeId("bookAccountSubscription")
                .log("bookAccountSubscription fired")
                .saga()
                .propagation(SagaPropagation.MANDATORY)
                .compensation("direct:cancelAccountSubscription").option("watchingBookingId", simple("${exchangeProperty.watchingBookingId}"))
                .process((exchange) ->
                {
                    exchange.getMessage().setBody(
                            Utils.prepareBookMovieSubscriptionRequest(exchange.getMessage().getBody(MovieWatchingRequest.class)));
                })
                .marshal(jaxbAccountSubscription)
                .to("spring-ws:http://" + accountSubscriptionServiceServer + "/soap-api/service/subscription")
                .to("stream:out")
                .unmarshal(jaxbAccountSubscription)
                .process((exchange) -> {
                    org.bp.accountsubscribing.BookMovieSubscriptionResponse bookMovieSubscriptionResponse =
                            exchange.getMessage().getBody(org.bp.accountsubscribing.BookMovieSubscriptionResponse.class);
                    String watchingId = exchange.getProperty("watchingBookingId", String.class);
                    bookingIdentifierService.assignAccountSubscriptionBookingId(watchingId, bookMovieSubscriptionResponse.getReturn().getId());

                    exchange.getProperty("paymentRequest", PaymentRequest.class).setWatchingId(watchingId);
                    exchange.getProperty("paymentRequest", PaymentRequest.class).setAccountsubscribingId(bookingIdentifierService.getAccountSubscriptionBookingId(watchingId));
                });

        from("direct:cancelAccountSubscription").routeId("cancelAccountSubscription")
                .log("cancelAccountSubscription fired")
                .process((exchange) -> {
                    String watchingId = exchange.getMessage().getHeader("watchingId", String.class);
                    int accountSubscriptionBookingId = bookingIdentifierService.getAccountSubscriptionBookingId(watchingId);
                    org.bp.accountsubscribing.CancelBooking cancelAccountSubscriptionRequest = new org.bp.accountsubscribing.CancelBooking();
                    cancelAccountSubscriptionRequest.setArg0(accountSubscriptionBookingId);
                    exchange.getMessage().setBody(cancelAccountSubscriptionRequest);
                })
                .marshal(jaxbAccountSubscription)
                .to("spring-ws:http://" + accountSubscriptionServiceServer + "/soap-api/service/subscription")
                .to("stream:out")
                .unmarshal(jaxbAccountSubscription);

        from("direct:payment").streamCaching().routeId("payment")
                .log("payment fired")
                .marshal().json()
                .removeHeaders("CamelHttp*")
                .to("rest:post:payment?host=" + paymentServiceServer)
                .to("stream:out")
                .unmarshal().json();

        from("direct:bookingPayment").streamCaching().routeId("payment/{id}")
                .log("get payment fired")
                .marshal().json()
                .removeHeaders("CamelHttp*")
                .to("rest:get:payment/{id}?host=" + paymentServiceServer)
                .to("stream:out")
                .unmarshal().json();
    }

}


