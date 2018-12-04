package com.pragmaticcoders.checkout.repository;

import com.pragmaticcoders.checkout.model.Basket;
import com.pragmaticcoders.checkout.model.BasketStatus;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BasketRepository extends CrudRepository<Basket, Long> {

    @Modifying
    @Query(value = "delete from basket where basket_id = ?", nativeQuery = true)
    void deleteById(Long itemId);

    @Modifying
    @Query(value = "update basket set status = ? where basket_id = ?", nativeQuery = true)
    void updateStatus(String status,  Long basketId);

    @Query(value = "select status from basket where basket_id = ?", nativeQuery = true)
    Optional<BasketStatus> findStatusByBasketId(Long basketId);
}
