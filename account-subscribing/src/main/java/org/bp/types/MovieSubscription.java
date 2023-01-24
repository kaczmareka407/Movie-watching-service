package org.bp.types;

import javax.xml.datatype.XMLGregorianCalendar;

public class MovieSubscription {
    protected String typeAccount;
    protected XMLGregorianCalendar from;
    protected XMLGregorianCalendar to;

    public String getTypeAccount() {
        return typeAccount;
    }

    public void setTypeAccount(String typeAccount) {
        this.typeAccount = typeAccount;
    }

    public XMLGregorianCalendar getFrom() {
        return from;
    }

    public void setFrom(XMLGregorianCalendar from) {
        this.from = from;
    }

    public XMLGregorianCalendar getTo() {
        return to;
    }

    public void setTo(XMLGregorianCalendar to) {
        this.to = to;
    }
}
