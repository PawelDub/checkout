package com.pragmaticcoders.checkout.repository;

import com.pragmaticcoders.checkout.model.BasketItem;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface BasketItemRepository extends CrudRepository<BasketItem, Long> {

    @Query(value = "Select * from basket_item where basket_id = ?", nativeQuery = true)
    Collection<BasketItem> findAllByBasketId(Long basketId);

    @Query(value = "Select * from basket_item where basket_id = ? and item_id = ?", nativeQuery = true)
    Optional<BasketItem> findByBasketIdAndItemId(Long basketId, Long itemId);

    @Modifying
    @Query(value = "delete from basket_item where basket_item_id = ?", nativeQuery = true)
    void deleteById(Long basketItemId);

}
