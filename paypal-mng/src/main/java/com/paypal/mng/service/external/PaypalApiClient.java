package com.paypal.mng.service.external;

import com.paypal.mng.service.dto.paypal.TrackerIdentifierListDTO;
import com.paypal.mng.service.dto.paypal.TrackerList;

public interface PaypalApiClient {
    TrackerIdentifierListDTO addTrackersBatch(String token, TrackerList trackerList);
}
