package com.pragmaticcoders.checkout.repository;

import com.pragmaticcoders.checkout.model.Discount;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscountRepository extends CrudRepository<Discount, Long> {
}
