package com.pragmaticcoders.checkout.service;

import com.pragmaticcoders.checkout.model.Discount;
import com.pragmaticcoders.checkout.repository.DiscountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class PriceDiscountService {

    //TODO Logger

    private DiscountRepository discountRepository;

    @Autowired
    public PriceDiscountService(DiscountRepository discountRepository) {
        this.discountRepository = discountRepository;
    }

    public Discount save(Discount discount) {
        return discountRepository.save(discount);
    }

    public Discount update(Discount discount) {
        return discountRepository.save(discount);
    }

    public void delete(Discount discount) {
        discountRepository.delete(discount);
    }

    public Iterable<Discount> findAll(){
        return discountRepository.findAll();
    }

    public Map<String, Discount> getDiscountAsMap() {
        Map<String, Discount> discountMap = new HashMap<>();
        this.findAll().forEach(discount -> {
            discountMap.put(discount.getType(), discount);
        });
        return discountMap;
    }
}
