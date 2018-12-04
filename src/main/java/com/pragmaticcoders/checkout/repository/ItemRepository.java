package com.pragmaticcoders.checkout.repository;

import com.pragmaticcoders.checkout.model.Item;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ItemRepository extends CrudRepository<Item, Long> {

    Optional<Item> findItemByType(String type);

    Optional<Item> findById(Long id);
}
