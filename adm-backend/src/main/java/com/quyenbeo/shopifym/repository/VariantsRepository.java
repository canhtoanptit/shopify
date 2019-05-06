package com.quyenbeo.shopifym.repository;

import com.quyenbeo.shopifym.domain.Variants;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Variants entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VariantsRepository extends JpaRepository<Variants, Long> {

}
