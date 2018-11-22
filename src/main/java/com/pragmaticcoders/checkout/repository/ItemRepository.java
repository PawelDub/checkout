package com.pragmaticcoders.checkout.repository;

import com.pragmaticcoders.checkout.model.Item;
import javassist.NotFoundException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface ItemRepository extends CrudRepository<Item, Long> {

    Collection<Item> findItemsByType(String type) throws NotFoundException;
}
