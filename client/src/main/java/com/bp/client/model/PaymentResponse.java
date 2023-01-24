package com.bp.client.model;

import java.time.OffsetDateTime;

public class PaymentResponse {
    private int transactionId;
    private OffsetDateTime transactionDate;
    private PaymentCard paymentCard;
    private int cost;
    private String watchingId;
    private int movieBookingId;
    private int movieSubscriptionBookingId;

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public OffsetDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(OffsetDateTime transactionDate) {
        this.transactionDate = transactionDate;
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

    public int getMovieSubscriptionBookingId() {
        return movieSubscriptionBookingId;
    }

    public void setMovieSubscriptionBookingId(int movieSubscriptionBookingId) {
        this.movieSubscriptionBookingId = movieSubscriptionBookingId;
    }
}
