package com.paypal.mng.service.mapper;

import com.paypal.mng.domain.*;
import com.paypal.mng.service.dto.PaypalHistoryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PaypalHistory} and its DTO {@link PaypalHistoryDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PaypalHistoryMapper extends EntityMapper<PaypalHistoryDTO, PaypalHistory> {



    default PaypalHistory fromId(Long id) {
        if (id == null) {
            return null;
        }
        PaypalHistory paypalHistory = new PaypalHistory();
        paypalHistory.setId(id);
        return paypalHistory;
    }
}
