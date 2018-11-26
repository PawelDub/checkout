package com.pragmaticcoders.checkout.repository;

import com.pragmaticcoders.checkout.model.BasketItem;
import javassist.NotFoundException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BasketItemRepository extends CrudRepository<BasketItem, Long> {

    Iterable<BasketItem> findAllByBasketId(Long basketId) throws NotFoundException;

    @Query(value = "Select * from basket_item where basket_id = ? and where item_id = ?", nativeQuery = true)
    BasketItem findByBasketIdAndItemId(Long basketId, Long itemId);
}
