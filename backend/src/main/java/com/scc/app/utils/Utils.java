package com.scc.app.utils;

import java.util.Objects;

public class Utils {

    public static boolean isFirebaseDatabase() {

        String property = System.getProperty("database.type");
        return Objects.equals(Constants.FIRABASE_DATABASE_TYPE, property);
    }
}
