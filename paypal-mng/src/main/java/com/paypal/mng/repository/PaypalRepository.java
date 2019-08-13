package com.paypal.mng.repository;

import com.paypal.mng.domain.Paypal;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Paypal entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PaypalRepository extends JpaRepository<Paypal, Long> {

}
