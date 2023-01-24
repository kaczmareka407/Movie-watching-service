package com.bp.client.model;

public class BookMovieRequest {
    private Person person;
    private Movie movie;
    private MovieSubscription movieSubscription;
    private PaymentCard paymentCard;

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public MovieSubscription getMovieSubscription() {
        return movieSubscription;
    }

    public void setMovieSubscription(MovieSubscription movieSubscription) {
        this.movieSubscription = movieSubscription;
    }

    public PaymentCard getPaymentCard() {
        return paymentCard;
    }

    public void setPaymentCard(PaymentCard paymentCard) {
        this.paymentCard = paymentCard;
    }
}
