package com.groupmeeting.test.base.util;

public class TestUtil {
    private TestUtil() {
        throw new IllegalStateException("util class");
    }

    public static String GRANT_TYPE = "Bearer";
    public static String USER_KEY = "USER";
    public static long ACCESS_TOKEN_EXPIRED_SECOND = 1814400000L;
    public static long REFRESH_TOKEN_EXPIRED_SECOND = 2592000000L;
    public static String SECRET_KEY = "testestestestestestestestestestestestestestestestestestestestestestestestestestestestestestestestes"
            + "testestestestestestestestestestestestestestestestest";
}
