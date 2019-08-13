package com.paypal.mng.service.mapper;

import com.paypal.mng.domain.*;
import com.paypal.mng.service.dto.PaypalDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Paypal} and its DTO {@link PaypalDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PaypalMapper extends EntityMapper<PaypalDTO, Paypal> {



    default Paypal fromId(Long id) {
        if (id == null) {
            return null;
        }
        Paypal paypal = new Paypal();
        paypal.setId(id);
        return paypal;
    }
}
