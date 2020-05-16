package com.scc.app.exception;


import com.scc.app.utils.Utils;

public class PaymentException extends Exception {

    public PaymentException(Long exceptionId) {
        super(Utils.getPaymentExceptionMessageById(exceptionId));
    }
}
