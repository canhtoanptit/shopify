package com.paypal.mng.service.mapper;

import com.paypal.mng.domain.*;
import com.paypal.mng.service.dto.StoreDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Store} and its DTO {@link StoreDTO}.
 */
@Mapper(componentModel = "spring", uses = {PaypalMapper.class})
public interface StoreMapper extends EntityMapper<StoreDTO, Store> {

    @Mapping(source = "paypal.id", target = "paypalId")
    StoreDTO toDto(Store store);

    @Mapping(source = "paypalId", target = "paypal")
    Store toEntity(StoreDTO storeDTO);

    default Store fromId(Long id) {
        if (id == null) {
            return null;
        }
        Store store = new Store();
        store.setId(id);
        return store;
    }
}
