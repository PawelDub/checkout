package com.pragmaticcoders.checkout.repository;

import com.pragmaticcoders.checkout.model.BasketItem;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BasketItemRepository extends CrudRepository<BasketItem, Long> {

    Iterable<BasketItem> findAllByBasketId(Long basketId);

    @Query(value = "Select * from basket_item where basket_id = ? and item_id = ?", nativeQuery = true)
    BasketItem findByBasketIdAndItemId(Long basketId, Long itemId);

    @Modifying
    @Query(value = "delete from basket_item where basket_item_id = ?", nativeQuery = true)
    void deleteById(Long basketItemId);

}
