package com.scc.app.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Constants {

    public static final long TOKEN_DURATION = TimeUnit.MINUTES.toMillis(10);
    public static final String FIRABASE_DATABASE_TYPE = "firebase";
    public static final String MYSQL_DATABASE_TYPE = "mysql";
    public static final long DELAY = TimeUnit.MINUTES.toMillis(10);
    public static final int DEFAULT_APPOINTMENTS_POSSIBILITIES = 5;
    public static final int MAX_TRIES = 1000;
    public static final Long RESOURCE_MIN_THRESHOLD = 500L;
    public static final Long RESOURCE_MULTIPLIER = 10L;
    public static final int MAX_SHIPMENT_DURATION_IN_HOURS = 24;
    public static final String DONE = "DONE";

    static final Map<Long, String> PAYMENT_EXCEPTION_ID_TO_MESSAGE = new HashMap<>();

    public static final long CARD_DETAILS_INVALID_EXCEPTION_ID = 1L;
    public static final long CARD_HOLDER_NAME_INVALID_EXCEPTION_ID = 2L;
    public static final long CARD_NO_INVALID_EXCEPTION_ID = 3L;
    public static final long CVV_INVALID_EXCEPTION_ID = 4L;
    public static final long EXPIRATION_DATE_INVALID_EXCEPTION_ID = 5L;
    public static final long INSUFFICIENT_FUNDS_EXCEPTION_ID = 6L;
    public static final long PAYMENT_FAILED_EXCEPTION_ID = 7L;
    public static final String PAYMENT_SUCCESSFUL = "Payment Successful!";

    static {
        PAYMENT_EXCEPTION_ID_TO_MESSAGE.put(CARD_DETAILS_INVALID_EXCEPTION_ID, "Card details are invalid!");
        PAYMENT_EXCEPTION_ID_TO_MESSAGE.put(CARD_HOLDER_NAME_INVALID_EXCEPTION_ID, "Card holder name is invalid!");
        PAYMENT_EXCEPTION_ID_TO_MESSAGE.put(CARD_NO_INVALID_EXCEPTION_ID, "Card number is invalid!");
        PAYMENT_EXCEPTION_ID_TO_MESSAGE.put(CVV_INVALID_EXCEPTION_ID, "CVV is invalid!");
        PAYMENT_EXCEPTION_ID_TO_MESSAGE.put(EXPIRATION_DATE_INVALID_EXCEPTION_ID, "Expiration date is invalid!");
        PAYMENT_EXCEPTION_ID_TO_MESSAGE.put(INSUFFICIENT_FUNDS_EXCEPTION_ID, "Insufficient funds! Pleas add funds or try another card!");
        PAYMENT_EXCEPTION_ID_TO_MESSAGE.put(PAYMENT_FAILED_EXCEPTION_ID, "Payment failed! Please check network connection and try again!");
    }

}
