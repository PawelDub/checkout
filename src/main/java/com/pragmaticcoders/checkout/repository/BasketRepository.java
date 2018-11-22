package com.pragmaticcoders.checkout.repository;

import com.pragmaticcoders.checkout.model.Basket;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BasketRepository extends CrudRepository<Basket, Long> {


}
