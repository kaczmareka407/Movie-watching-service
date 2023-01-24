package org.bp.movie;

import org.bp.BookMovieRequest;
import org.bp.CancelBookingRequest;
import org.bp.types.BookingInfo;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@org.springframework.stereotype.Service
public class MovieBookingEndpoint implements MovieBooking {

    @Override
    public BookingInfo cancelBooking(CancelBookingRequest payload) throws MovieFaultMsg {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public BookingInfo bookMovie(BookMovieRequest payload) throws MovieFaultMsg {
        Random rand = new Random();
        if (payload != null && payload.getMovie() != null
                && "China".equals(payload.getMovie().getCountry())) {
            org.bp.types.Fault movieFault = new org.bp.types.Fault();
            movieFault.setCode(10);
            movieFault.setText("Movie from China are censored in this country");
            MovieFaultMsg fault = new MovieFaultMsg("Movie from this country is not available",
                    movieFault);
            throw fault;
        }
        BookingInfo response = new BookingInfo();
        response.setId(Math.abs(rand.nextInt()));
        response.setCost(new java.math.BigDecimal(Math.abs(rand.nextInt(6) + 5)));
        return response;

    }

}
