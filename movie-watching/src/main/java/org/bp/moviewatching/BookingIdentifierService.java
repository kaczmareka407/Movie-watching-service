package org.bp.moviewatching;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.UUID;

@Service
public class BookingIdentifierService {
    HashMap<String, BookingIds> bookingIdsMap = new HashMap<>();

    public String generateBookingId() {
        String bookingID = UUID.randomUUID().toString();
        BookingIds bookingIds = new BookingIds();
        bookingIdsMap.put(bookingID, bookingIds);

        return bookingID;
    }

    public void assignMovieBookingId(String watchingBookingId, int movieBookingId) {
        bookingIdsMap.get(watchingBookingId).setMovieBookingId(movieBookingId);
    }

    public void assignAccountSubscriptionBookingId(String watchingBookingId, int accountSubscriptionBookingId) {
        bookingIdsMap.get(watchingBookingId).setAccountSubscriptionBookingId(accountSubscriptionBookingId);
    }

    public int getMovieBookingId(String watchingBookingId) {
        return bookingIdsMap.get(watchingBookingId).getMovieBookingId();
    }

    public int getAccountSubscriptionBookingId(String watchingBookingId) {
        return bookingIdsMap.get(watchingBookingId).getAccountSubscriptionBookingId();
    }

    public static class BookingIds {
        private int movieBookingId;
        private int accountSubscriptionBookingId;

        public int getMovieBookingId() {
            return movieBookingId;
        }

        public void setMovieBookingId(int movieBookingId) {
            this.movieBookingId = movieBookingId;
        }

        public int getAccountSubscriptionBookingId() {
            return accountSubscriptionBookingId;
        }

        public void setAccountSubscriptionBookingId(int parkingSubscriptionBookingId) {
            this.accountSubscriptionBookingId = parkingSubscriptionBookingId;
        }
    }

}