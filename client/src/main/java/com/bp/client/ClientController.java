package com.bp.client;

import com.bp.client.model.BookMovieRequest;
import com.bp.client.model.PaymentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.remoting.soap.SoapFaultException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ClientController {

    @org.springframework.beans.factory.annotation.Value("${moviewatching.service.server}")
    private String movieWatchingServiceServer;

    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/watching")
    public String bookingForm(Model model) {
        model.addAttribute(new BookMovieRequest());

        return "watching";
    }

    @GetMapping("/watching/payment")
    public String paymentForm(Model model) {
        model.addAttribute(new String());

        return "payment";
    }

    @GetMapping("/watching/payment/{id}")
    public String getPayment(@PathVariable String id, Model model) {

        try {
            ResponseEntity<PaymentResponse> response = restTemplate.getForEntity(String.format("http://" + movieWatchingServiceServer + "/api/moviewatching/watching/payment/%s", id), PaymentResponse.class);
            model.addAttribute("paymentResponse", response.getBody());

            return "result";

        } catch (Exception e) {
            String eS = "Cannot find ID";
            model.addAttribute("fault", eS);

            return "fault";
        }

    }

    @PostMapping("/watching")
    public String booking(@ModelAttribute("bookMovieRequest") BookMovieRequest request, Model model) {

        try {
            model.addAttribute("watching", request);
            ResponseEntity<PaymentResponse> response = restTemplate.postForEntity("http://" + movieWatchingServiceServer + "/api/moviewatching/watching", request, PaymentResponse.class);
            model.addAttribute("paymentResponse", response.getBody());


            return "result";

        } catch (Exception e) {
            String eS = e.toString().replaceFirst("500 : \"org\\.springframework\\.ws\\.soap\\.client\\.SoapFaultClientException: ", "");
            eS = eS.replace("org.springframework.web.client.HttpServerErrorException$InternalServerError:", "");
            eS = eS.substring(0, 40);
            System.out.println(eS);
            model.addAttribute("fault", eS);

            return "fault";
        }
    }

    @PostMapping("/watching/payment")
    public ModelAndView redirect(@ModelAttribute("id") String id) {
        return new ModelAndView(String.format("redirect:/watching/payment/%s", id));
    }

}
