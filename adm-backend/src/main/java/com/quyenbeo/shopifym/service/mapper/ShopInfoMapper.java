package com.quyenbeo.shopifym.service.mapper;

import com.quyenbeo.shopifym.domain.*;
import com.quyenbeo.shopifym.service.dto.ShopInfoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ShopInfo and its DTO ShopInfoDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface ShopInfoMapper extends EntityMapper<ShopInfoDTO, ShopInfo> {


    @Mapping(target = "products", ignore = true)
    ShopInfo toEntity(ShopInfoDTO shopInfoDTO);

    default ShopInfo fromId(Long id) {
        if (id == null) {
            return null;
        }
        ShopInfo shopInfo = new ShopInfo();
        shopInfo.setId(id);
        return shopInfo;
    }
}
