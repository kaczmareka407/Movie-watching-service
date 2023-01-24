package org.bp.accountsubscribing;

import org.bp.types.Fault;

public class AccountSubscribingFaultMsg extends Exception {
    private Fault accountSubscribingFault;

    public Fault getAccountSubscribingFault() {
        return accountSubscribingFault;
    }

    public void setAccountSubscribingFault(Fault accountSubscribingFault) {
        this.accountSubscribingFault = accountSubscribingFault;
    }

    public AccountSubscribingFaultMsg() {
        super();
    }

    public AccountSubscribingFaultMsg(String message) {
        super(message);
    }

    public AccountSubscribingFaultMsg(String message, java.lang.Throwable cause) {
        super(message, cause);
    }

    public AccountSubscribingFaultMsg(String message, org.bp.types.Fault accountSubscribingFault) {
        super(message);
        this.accountSubscribingFault = accountSubscribingFault;
    }

    public AccountSubscribingFaultMsg(String message, org.bp.types.Fault accountSubscribingFault, java.lang.Throwable cause) {
        super(message, cause);
        this.accountSubscribingFault = accountSubscribingFault;
    }

    public org.bp.types.Fault getFaultInfo() {
        return this.accountSubscribingFault;
    }
}
