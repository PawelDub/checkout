package com.pragmaticcoders.checkout.service;

import com.pragmaticcoders.checkout.model.Basket;
import com.pragmaticcoders.checkout.model.Item;
import com.pragmaticcoders.checkout.repository.BasketRepository;
import javafx.scene.transform.Scale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class BasketService {

    private BasketRepository basketRepository;

    @Autowired
    public BasketService(BasketRepository basketRepository) {
        this.basketRepository = basketRepository;
    }

    public Basket open() {
        return save(new Basket());
    }

    public Basket save(Basket basket) {
        return basketRepository.save(basket);
    }

    public Basket update(Basket basket) {
        return basketRepository.save(basket);
    }

    public Basket close(Basket basket) {
        return coutTotalPrice(basket);
    }

    public void delete(Long basketId) {
        basketRepository.deleteById(basketId);
    }

    public Basket coutTotalPrice(Basket basket) {
        BigDecimal totalPrice = new BigDecimal("0.00");
        //TODO discount strategy
        basket.setTotalPrice(totalPrice);
        return basket;
    }
}
