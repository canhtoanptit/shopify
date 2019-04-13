package com.quyenbeo.shopifym.repository;

import com.quyenbeo.shopifym.domain.ShopInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the ShopInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ShopinfoRepository extends JpaRepository<ShopInfo, Long> {

    @Query(value = "select distinct shopinfo from ShopInfo shopinfo left join fetch shopinfo.users",
        countQuery = "select count(distinct shopinfo) from ShopInfo shopinfo")
    Page<ShopInfo> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct shopinfo from ShopInfo shopinfo left join fetch shopinfo.users")
    List<ShopInfo> findAllWithEagerRelationships();

    @Query("select shopinfo from ShopInfo shopinfo left join fetch shopinfo.users where shopinfo.id =:id")
    Optional<ShopInfo> findOneWithEagerRelationships(@Param("id") Long id);

}
