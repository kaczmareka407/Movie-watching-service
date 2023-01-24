package org.bp.payment.model;

public class PaymentNotFoundException extends RuntimeException {

    public PaymentNotFoundException(String msg) {
        super(msg);
    }
}