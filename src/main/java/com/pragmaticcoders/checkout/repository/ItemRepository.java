package com.pragmaticcoders.checkout.repository;

import com.pragmaticcoders.checkout.model.Item;
import javassist.NotFoundException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends CrudRepository<Item, Long> {

    Iterable<Item> findAllItemsByType(String type) throws NotFoundException;
}
