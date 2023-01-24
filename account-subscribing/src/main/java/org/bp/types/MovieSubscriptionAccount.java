package org.bp.types;

public class MovieSubscriptionAccount {
    private Person person;
    private MovieSubscription movieSubscription;

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public MovieSubscription getMovieSubscription() {
        return movieSubscription;
    }

    public void setMovieSubscription(MovieSubscription movieSubscription) {
        this.movieSubscription = movieSubscription;
    }
}

