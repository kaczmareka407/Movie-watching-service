package org.bp.payment.model;

public class PaymentRequest {
    private PaymentCard paymentCard;
    private int amount;

    private String watchingId;
    private int movieBookingId;
    private int accountsubscribingId;


    public PaymentCard getPaymentCard() {
        return paymentCard;
    }

    public void setPaymentCard(PaymentCard paymentCard) {
        this.paymentCard = paymentCard;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getWatchingId() {
        return watchingId;
    }

    public void setWatchingId(String watchingId) {
        this.watchingId = watchingId;
    }

    public int getMovieBookingId() {
        return movieBookingId;
    }

    public void setMovieBookingId(int movieBookingId) {
        this.movieBookingId = movieBookingId;
    }

    public int getAccountsubscribingId() {
        return accountsubscribingId;
    }

    public void setAccountsubscribingId(int accountsubscribingId) {
        this.accountsubscribingId = accountsubscribingId;
    }
}
