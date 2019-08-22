package com.paypal.mng.repository;

import com.paypal.mng.domain.Tracking;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Spring Data  repository for the Tracking entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TrackingRepository extends JpaRepository<Tracking, Long> {
    Optional<Tracking> findByTrackingNumber(String trackingNumber);

    @Query(value = "select * from tracking t inner join jhi_order o ON t.order_id = o.id where o.order_name = ?1 and t.tracking_number = ?2", nativeQuery = true)
    Optional<Tracking> findByOrderNameAndTrackingNumber(String orderName, String trackingNumber);
}
