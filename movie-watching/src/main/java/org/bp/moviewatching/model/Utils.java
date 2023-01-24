package org.bp.moviewatching.model;

import java.time.OffsetDateTime;

public class Utils {


    public static org.bp.BookMovieRequest prepareMovieBookingRequest(MovieWatchingRequest movieWatchingRequest) {
        org.bp.BookMovieRequest bookMovieRequest = new org.bp.BookMovieRequest();
        bookMovieRequest.setMovie(movieWatchingRequest.getMovie());

        org.bp.Person person = new org.bp.Person();
        if (movieWatchingRequest.getPerson() != null) {
            person.setFirstName(movieWatchingRequest.getPerson().getFirstName());
            person.setLastName(movieWatchingRequest.getPerson().getLastName());
        }

        bookMovieRequest.setPerson(person);

        return bookMovieRequest;
    }

    public static org.bp.CancelBookingRequest prepareMovieCancelRequest(org.bp.BookingInfo bookingInfo) {
        org.bp.CancelBookingRequest cancelBookingRequest = new org.bp.CancelBookingRequest();
        cancelBookingRequest.setBookingId(bookingInfo.getId());

        return cancelBookingRequest;
    }


    public static org.bp.accountsubscribing.BookMovieSubscription prepareBookMovieSubscriptionRequest(MovieWatchingRequest movieWatchingRequest) {
        org.bp.accountsubscribing.BookMovieSubscription bookMovieSubscription = new org.bp.accountsubscribing.BookMovieSubscription();
        org.bp.accountsubscribing.MovieSubscriptionAccount movieSubscriptionAccount = new org.bp.accountsubscribing.MovieSubscriptionAccount();

        movieSubscriptionAccount.setMovieSubscription(movieWatchingRequest.getMovieSubscription());
        movieSubscriptionAccount.setPerson(movieWatchingRequest.getPerson());


        bookMovieSubscription.setArg0(movieSubscriptionAccount);

        return bookMovieSubscription;
    }

    public static org.bp.accountsubscribing.CancelBooking prepareCancelSubscriptionCancelRequest(org.bp.accountsubscribing.BookMovieSubscriptionResponse bookMovieSubscriptionResponse) {
        org.bp.accountsubscribing.CancelBooking cancelBookingRequest = new org.bp.accountsubscribing.CancelBooking();
        cancelBookingRequest.setArg0(bookMovieSubscriptionResponse.getReturn().getId());

        return cancelBookingRequest;
    }


    public static org.bp.payment.PaymentRequest preparePaymentRequest(MovieWatchingRequest movieWatchingRequest) {
        org.bp.payment.PaymentRequest paymentRequest = new org.bp.payment.PaymentRequest();
        paymentRequest.setPaymentCard(movieWatchingRequest.getPaymentCard());
        paymentRequest.setAmount(0);
        paymentRequest.setWatchingId("");
        paymentRequest.setMovieBookingId(-1);
        paymentRequest.setAccountsubscribingId(-1);

        return paymentRequest;
    }

    public static org.bp.payment.PaymentResponse createPaymentResponse() {
        org.bp.payment.PaymentResponse paymentResponse = new org.bp.payment.PaymentResponse();
        paymentResponse.setTransactionId(43434);
        paymentResponse.setCost(0);
        paymentResponse.setWatchingId("");
        paymentResponse.setMovieBookingId(-1);
        paymentResponse.setAccountsubscribingId(-1);

        return paymentResponse;
    }

}
