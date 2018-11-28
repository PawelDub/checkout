package com.pragmaticcoders.checkout.repository;

import com.pragmaticcoders.checkout.model.PriceDiscount;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceDiscountRepository extends CrudRepository<PriceDiscount, Long> {

    @Modifying
    @Query(value = "delete from price_discount where discount_id = ?", nativeQuery = true)
    void deleteById(Long itemId);
}
