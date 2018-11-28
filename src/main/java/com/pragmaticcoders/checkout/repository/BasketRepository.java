package com.pragmaticcoders.checkout.repository;

import com.pragmaticcoders.checkout.model.Basket;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BasketRepository extends CrudRepository<Basket, Long> {

    @Modifying
    @Query(value = "delete from basket where basket_id = ?", nativeQuery = true)
    void deleteById(Long itemId);
}
