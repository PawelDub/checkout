package com.pragmaticcoders.checkout.service;

import com.pragmaticcoders.checkout.model.PriceDiscount;
import com.pragmaticcoders.checkout.repository.PriceDiscountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class PriceDiscountService {

    //TODO Logger

    private PriceDiscountRepository discountRepository;

    @Autowired
    public PriceDiscountService(PriceDiscountRepository discountRepository) {
        this.discountRepository = discountRepository;
    }

    public PriceDiscount save(PriceDiscount discount) {
        return discountRepository.save(discount);
    }

    public PriceDiscount update(PriceDiscount discount) {
        return discountRepository.save(discount);
    }

    public void delete(PriceDiscount discount) {
        discountRepository.delete(discount);
    }

    public Iterable<PriceDiscount> findAll(){
        return discountRepository.findAll();
    }

    public Map<String, PriceDiscount> getDiscountAsMap() {
        Map<String, PriceDiscount> discountMap = new HashMap<>();
        this.findAll().forEach(discount -> {
            discountMap.put(discount.getType(), discount);
        });
        return discountMap;
    }
}
