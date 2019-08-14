package com.paypal.mng.service.util;

import org.springframework.http.HttpHeaders;
import org.springframework.web.util.UriComponentsBuilder;

import java.nio.charset.Charset;
import java.util.Base64;
import java.util.Map;

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

    public static UriComponentsBuilder buildQuery(String url, Map<String, String> uriParams) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        uriParams.forEach(builder::queryParam);
        return builder;
    }
}
