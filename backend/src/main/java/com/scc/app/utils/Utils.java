package com.scc.app.utils;

import java.util.Objects;
import java.util.Random;

public class Utils {

    private static final Random RANDOM_GENERATOR = new Random();

    public static boolean isFirebaseDatabase() {

        String property = System.getProperty("database.type");
        return Objects.equals(Constants.FIRABASE_DATABASE_TYPE, property);
    }

    public static int getRandom(Integer bound) {
        return RANDOM_GENERATOR.nextInt(bound);
    }

    public static String getPaymentExceptionMessageById(long id) {
        return Constants.PAYMENT_EXCEPTION_ID_TO_MESSAGE.getOrDefault(id, "ERROR!");
    }
}
