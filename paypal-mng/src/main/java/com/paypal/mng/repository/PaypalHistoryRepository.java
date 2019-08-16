package com.paypal.mng.repository;

import com.paypal.mng.domain.PaypalHistory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PaypalHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PaypalHistoryRepository extends JpaRepository<PaypalHistory, Long> {

}
