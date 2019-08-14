package com.paypal.mng.service.util;

import org.springframework.http.HttpHeaders;

import java.nio.charset.Charset;
import java.util.Base64;

public class RestUtil {
    public static HttpHeaders createHeaders(String username, String password) {
        return new HttpHeaders() {{
            String auth = username + ":" + password;
            byte[] encodedAuth = Base64.getEncoder().encode(
                auth.getBytes(Charset.forName("US-ASCII")));
            String authHeader = "Basic " + new String(encodedAuth);
            set("Authorization", authHeader);
        }};
    }
}
