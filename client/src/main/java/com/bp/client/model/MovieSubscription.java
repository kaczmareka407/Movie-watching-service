package com.bp.client.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class MovieSubscription {

    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private Date from;
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private Date to;

    private String typeAccount;

    public Date getFrom() {
        return from;
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    public Date getTo() {
        return to;
    }

    public void setTo(Date to) {
        this.to = to;
    }

    public String getTypeAccount() {
        return typeAccount;
    }

    public void setTypeAccount(String typeAccount) {
        this.typeAccount = typeAccount;
    }
}
