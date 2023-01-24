package org.bp.moviewatching.model;


public class MovieWatchingRequest {
    private org.bp.accountsubscribing.Person person;
    private org.bp.Movie movie;
    private org.bp.accountsubscribing.MovieSubscription movieSubscription;
    private org.bp.payment.PaymentCard paymentCard;

    public org.bp.accountsubscribing.Person getPerson() {
        return person;
    }

    public void setPerson(org.bp.accountsubscribing.Person person) {
        this.person = person;
    }

    public org.bp.Movie getMovie() {
        return movie;
    }

    public void setMovie(org.bp.Movie movie) {
        this.movie = movie;
    }

    public org.bp.accountsubscribing.MovieSubscription getMovieSubscription() {
        return movieSubscription;
    }

    public void setMovieSubscription(org.bp.accountsubscribing.MovieSubscription movieSubscription) {
        this.movieSubscription = movieSubscription;
    }

    public org.bp.payment.PaymentCard getPaymentCard() {
        return paymentCard;
    }

    public void setPaymentCard(org.bp.payment.PaymentCard paymentCard) {
        this.paymentCard = paymentCard;
    }
}
