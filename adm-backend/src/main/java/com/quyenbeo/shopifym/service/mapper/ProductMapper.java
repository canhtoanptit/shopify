package com.quyenbeo.shopifym.service.mapper;

import com.quyenbeo.shopifym.domain.*;
import com.quyenbeo.shopifym.service.dto.ProductDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Product and its DTO ProductDTO.
 */
@Mapper(componentModel = "spring", uses = {ShopInfoMapper.class})
public interface ProductMapper extends EntityMapper<ProductDTO, Product> {

    @Mapping(source = "shopInfo.id", target = "shopInfoId")
    ProductDTO toDto(Product product);

    @Mapping(source = "shopInfoId", target = "shopInfo")
    Product toEntity(ProductDTO productDTO);

    default Product fromId(Long id) {
        if (id == null) {
            return null;
        }
        Product product = new Product();
        product.setId(id);
        return product;
    }
}
