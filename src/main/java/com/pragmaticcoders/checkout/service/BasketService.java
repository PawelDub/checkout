package com.pragmaticcoders.checkout.service;

import com.pragmaticcoders.checkout.exceptions.BasketStatusException;
import com.pragmaticcoders.checkout.model.Basket;
import com.pragmaticcoders.checkout.repository.BasketRepository;
import com.pragmaticcoders.checkout.service.discount.price.PriceStrategyService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class BasketService {

    //TODO Logger

    private BasketRepository basketRepository;
    private PriceStrategyService priceStrategyService;

    @Autowired
    public BasketService(BasketRepository basketRepository, PriceStrategyService priceStrategyService) {
        this.basketRepository = basketRepository;
        this.priceStrategyService = priceStrategyService;
    }

    public Basket open() {
        return save(new Basket(Basket.BasketStatus.NEW, new BigDecimal("0.00")));
    }

    private Basket save(Basket basket) {
        return basketRepository.save(basket);
    }

    public Basket update(Basket basket) throws BasketStatusException {
        if (basket.getStatus().equals(Basket.BasketStatus.NEW)) basket.setStatus(Basket.BasketStatus.ACTIVE);
        if (!basket.getStatus().equals(Basket.BasketStatus.ACTIVE))
            throw new BasketStatusException(String.format("%s basket can not be updated", basket.getStatus().toString()));
        return basketRepository.save(basket);
    }

    public Optional<Basket> findById(Long basketId) {
        return basketRepository.findById(basketId);
    }

    public Basket close(Basket basket) {
        basket.setStatus(Basket.BasketStatus.CLOSED);
        basket = countTotalPrice(basket);
        return save(basket);
    }

    public void delete(Long basketId) throws BasketStatusException, NotFoundException {
        Basket basket = findById(basketId).orElseThrow(() -> new NotFoundException("User not found"));
        if(basket.getStatus().equals(Basket.BasketStatus.CLOSED)) {
            throw new BasketStatusException(String.format("%s basket can not be deleted", basket.getStatus().toString()));
        } else {
            basketRepository.deleteById(basketId);
        }
    }

    public Basket countTotalPrice(Basket basket) {
        basket.setTotalPrice(priceStrategyService.getFinalPrice(basket));
        return basket;
    }

}
