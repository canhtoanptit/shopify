package com.paypal.mng.service.external;

import com.paypal.mng.config.ApplicationProperties;
import com.paypal.mng.service.dto.paypal.TokenDTO;
import com.paypal.mng.service.dto.paypal.TrackerIdentifierListDTO;
import com.paypal.mng.service.dto.paypal.TrackerList;
import com.paypal.mng.service.util.RestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collections;

@Service
public class PaypalApiClientImpl implements PaypalApiClient {

    private final Logger log = LoggerFactory.getLogger(PaypalApiClientImpl.class);

    private final RestTemplate restTemplate;

    private final ApplicationProperties applicationProperties;

    public PaypalApiClientImpl(RestTemplate restTemplate, ApplicationProperties applicationProperties) {
        this.restTemplate = restTemplate;
        this.applicationProperties = applicationProperties;
    }

    @Override
    public TrackerIdentifierListDTO addTrackersBatch(String token, TrackerList trackerList) {
        log.info("add tracker list {}", trackerList);
        String url = applicationProperties.getPaypal().getHost() + "/v1/shipping/trackers-batch";
        ResponseEntity<TrackerIdentifierListDTO> rs = restTemplate.exchange(url, HttpMethod.POST,
            new HttpEntity<>(trackerList, RestUtil.createHeaders(token)), TrackerIdentifierListDTO.class);
        log.info("Status code of api call {} with body {}", rs.getStatusCode(), rs.getBody());
        return rs.getBody();
    }

    @Override
    public TokenDTO getToken(String paypalClientId, String paypalSecret) {
        String url = applicationProperties.getPaypal().getHost() + "/v1/oauth2/token";
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        String auth = paypalClientId + ":" + paypalSecret;
        byte[] encodedAuth = Base64.getEncoder().encode(
            auth.getBytes(StandardCharsets.US_ASCII));
        String authHeader = "Basic " + new String(encodedAuth);
        headers.set("Authorization", authHeader);
        MultiValueMap<String, String> map =
            new LinkedMultiValueMap<>();
        map.add("grant_type", "client_credentials");
        ResponseEntity<TokenDTO> rs = restTemplate.exchange(url, HttpMethod.POST,
            new HttpEntity<>(map, headers), TokenDTO.class);
        return rs.getBody();
    }
}
