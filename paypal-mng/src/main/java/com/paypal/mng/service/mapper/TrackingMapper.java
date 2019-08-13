package com.paypal.mng.service.mapper;

import com.paypal.mng.domain.*;
import com.paypal.mng.service.dto.TrackingDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Tracking} and its DTO {@link TrackingDTO}.
 */
@Mapper(componentModel = "spring", uses = {OrderMapper.class})
public interface TrackingMapper extends EntityMapper<TrackingDTO, Tracking> {

    @Mapping(source = "order.id", target = "orderId")
    TrackingDTO toDto(Tracking tracking);

    @Mapping(source = "orderId", target = "order")
    Tracking toEntity(TrackingDTO trackingDTO);

    default Tracking fromId(Long id) {
        if (id == null) {
            return null;
        }
        Tracking tracking = new Tracking();
        tracking.setId(id);
        return tracking;
    }
}
