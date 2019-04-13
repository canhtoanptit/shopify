package com.quyenbeo.shopifym.service.mapper;

import com.quyenbeo.shopifym.domain.*;
import com.quyenbeo.shopifym.service.dto.ShopinfoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ShopInfo and its DTO ShopinfoDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface ShopinfoMapper extends EntityMapper<ShopinfoDTO, ShopInfo> {



    default ShopInfo fromId(Long id) {
        if (id == null) {
            return null;
        }
        ShopInfo shopinfo = new ShopInfo();
        shopinfo.setId(id);
        return shopinfo;
    }
}
