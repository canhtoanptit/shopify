package com.paypal.mng.service.external;

import com.paypal.mng.config.ApplicationProperties;
import com.paypal.mng.service.dto.paypal.TrackerIdentifierListDTO;
import com.paypal.mng.service.dto.paypal.TrackerList;
import com.paypal.mng.service.util.RestUtil;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PaypalApiClientImpl implements PaypalApiClient {

    private final RestTemplate restTemplate;

    private final ApplicationProperties applicationProperties;

    public PaypalApiClientImpl(RestTemplate restTemplate, ApplicationProperties applicationProperties) {
        this.restTemplate = restTemplate;
        this.applicationProperties = applicationProperties;
    }

    @Override
    public TrackerIdentifierListDTO addTrackersBatch(String token, TrackerList trackerList) {
        String url = applicationProperties.getPaypal().getHost() + "/v1/shipping/trackers-batch";
        ResponseEntity<TrackerIdentifierListDTO> rs = restTemplate.exchange(url, HttpMethod.POST,
            new HttpEntity<>(trackerList, RestUtil.createHeaders(token)), TrackerIdentifierListDTO.class);
        return rs.getBody();
    }
}
