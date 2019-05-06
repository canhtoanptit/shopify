package com.quyenbeo.shopifym.service.mapper;

import com.quyenbeo.shopifym.domain.*;
import com.quyenbeo.shopifym.service.dto.VariantsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Variants and its DTO VariantsDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface VariantsMapper extends EntityMapper<VariantsDTO, Variants> {



    default Variants fromId(Long id) {
        if (id == null) {
            return null;
        }
        Variants variants = new Variants();
        variants.setId(id);
        return variants;
    }
}
