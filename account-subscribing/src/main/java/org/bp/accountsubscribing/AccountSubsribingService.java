package org.bp.accountsubscribing;

import org.bp.types.Fault;
import org.bp.types.MovieSubscriptionAccount;
import org.bp.types.MovieSubscription;
import org.bp.types.SubscribingInfo;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

@javax.jws.WebService
@org.springframework.stereotype.Service
public class AccountSubsribingService {

    static final List<String> accountTypes = Arrays.asList("child", "normal", "premium");

    Random rand = new Random();

    public SubscribingInfo bookMovieSubscription(MovieSubscriptionAccount movieSubscriptionAccount) throws AccountSubscribingFaultMsg {

        //GregorianCalendar c = new GregorianCalendar();
        //java.util.Date date = new java.util.Date();
        //c.setTime(date);
        //System.out.println("CCC:");
        //System.out.println(c);


        if (movieSubscriptionAccount != null) {
            MovieSubscription movieSubscription = movieSubscriptionAccount.getMovieSubscription();

            System.out.println("movieSubscription:");
            System.out.println(movieSubscription.getFrom().toGregorianCalendar());

            if (movieSubscription != null && movieSubscription.getFrom() != null && movieSubscription.getTo() != null &&
                    ((movieSubscription.getFrom().toGregorianCalendar().compareTo(movieSubscription.getTo().toGregorianCalendar()) == 1)
                            // ||
                            // (movieSubscription.getFrom().toGregorianCalendar().compareTo(c) == 1 &&
                            //  movieSubscription.getTo().toGregorianCalendar().compareTo(c) == 1))
                    )) {

                Fault parkingFault = new Fault();
                parkingFault.setCode(5);
                parkingFault.setText("Incorrect dates");

                AccountSubscribingFaultMsg fault = new AccountSubscribingFaultMsg("Please, type correct dates                 ", parkingFault);
                throw fault;

            } else if (movieSubscription != null && !accountTypes.contains(movieSubscription.getTypeAccount())) {
                Fault movieFault = new Fault();
                movieFault.setCode(10);
                movieFault.setText("No match types");

                AccountSubscribingFaultMsg fault = new AccountSubscribingFaultMsg("Available types: child, normal, premium", movieFault);
                throw fault;

            }
        }

        SubscribingInfo subscribingInfo = new SubscribingInfo();
        subscribingInfo.setId(Math.abs(rand.nextInt()));
        if (movieSubscriptionAccount != null && movieSubscriptionAccount.getMovieSubscription() != null &&
                movieSubscriptionAccount.getMovieSubscription().getFrom() != null &&
                movieSubscriptionAccount.getMovieSubscription().getTo() != null) {


            MovieSubscription movieSubscription = movieSubscriptionAccount.getMovieSubscription();
            DatatypeFactory dtf = null;
            try {
                dtf = DatatypeFactory.newInstance();
            } catch (DatatypeConfigurationException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


            //subscribingInfo.setId(Math.abs(rand.nextInt()));
            int duration = dtf.newDuration(movieSubscription.getTo().toGregorianCalendar().getTimeInMillis() - movieSubscription.getFrom().toGregorianCalendar().getTimeInMillis()).getDays();
            subscribingInfo.setId(Math.abs(rand.nextInt()));

            switch (movieSubscriptionAccount.getMovieSubscription().getTypeAccount()) {
                case "child":
                    subscribingInfo.setCost(10 * duration);
                    break;
                case "normal":
                    subscribingInfo.setCost(20 * duration);
                    break;
                case "premium":
                    subscribingInfo.setCost(30 * duration);
                    break;
                default:
                    break;
            }
        } else {
            subscribingInfo.setCost(0);
        }
        return subscribingInfo;

    }

    public SubscribingInfo cancelBooking(int subscribingId) throws AccountSubscribingFaultMsg {
        return null;
    }
}
