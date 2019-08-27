package com.paypal.mng.config;

/**
 * Application constants.
 */
public final class Constants {

    // Regex for acceptable logins
    public static final String LOGIN_REGEX = "^[_.@A-Za-z0-9-]*$";

    public static final String SYSTEM_ACCOUNT = "system";
    public static final String DEFAULT_LANGUAGE = "en";
    public static final String ANONYMOUS_USER = "anonymoususer";


    // Paypal api call history
    public static final int CALLED = 0;
    public static final int PAYPAL_ADD_TRACKING_SUCCESS = 1;

    // csv file upload
    public static String ORDER_PROCESSED = "PROCESSED";

    public static String ORDER_NOT_PROCESS = "NOT PROCESS";

    // Transaction status
    public static String TRANSACTION_STATUS_SUCCESS = "success";

    private Constants() {
    }
}
