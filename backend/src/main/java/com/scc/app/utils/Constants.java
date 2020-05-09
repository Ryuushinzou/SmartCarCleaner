package com.scc.app.utils;

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
}
