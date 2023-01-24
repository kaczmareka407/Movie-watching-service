package org.bp.payment.model;

import java.util.Date;

public class PaymentResponse {
    private int transactionId;
    //    private Date transactionDate;
    private PaymentCard paymentCard;
    private int cost;
    private String watchingId;
    private int movieBookingId;
    private int accountsubscribingId;

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public PaymentCard getPaymentCard() {
        return paymentCard;
    }

    public void setPaymentCard(PaymentCard paymentCard) {
        this.paymentCard = paymentCard;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
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
