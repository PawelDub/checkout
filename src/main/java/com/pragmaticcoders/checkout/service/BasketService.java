package com.pragmaticcoders.checkout.service;

import com.pragmaticcoders.checkout.exceptions.BasketStatusException;
import com.pragmaticcoders.checkout.model.Basket;
import com.pragmaticcoders.checkout.model.BasketItem;
import com.pragmaticcoders.checkout.repository.BasketRepository;
import com.pragmaticcoders.checkout.service.discount.price.PriceStrategyService;
import javassist.NotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BasketService {

    Logger logger = LogManager.getLogger(PriceStrategyService.class);

    private BasketItemService basketItemService;
    private BasketRepository basketRepository;
    private PriceStrategyService priceStrategyService;

    @Autowired
    public BasketService(BasketRepository basketRepository, PriceStrategyService priceStrategyService, BasketItemService basketItemService) {
        this.basketRepository = basketRepository;
        this.priceStrategyService = priceStrategyService;
        this.basketItemService = basketItemService;
    }

    public Basket open() {
        Basket basket = this.save(new Basket(Basket.BasketStatus.NEW, new BigDecimal("0.00")));
        logger.info("New Basket {} was opened", basket.toString());
        return basket;
    }

    private Basket save(Basket basket) {
        return basketRepository.save(basket);
    }

    @Transactional
    public Basket update(Basket basket) throws BasketStatusException {
        if (basket.getStatus().equals(Basket.BasketStatus.NEW)) {
            logger.info("New Basket {} status was updated to ACTIVE status", basket.toString());
            basket.setStatus(Basket.BasketStatus.ACTIVE);
        }

        if (!basket.getStatus().equals(Basket.BasketStatus.ACTIVE)) {
            logger.error("Basket {} can not be updated because of status {}", basket.toString(), basket.getStatus());
            throw new BasketStatusException(String.format("%s basket can not be updated", basket.getStatus().toString()));
        }

        List<BasketItem> basketItems = (List<BasketItem>) basketItemService.findAllByBasketId(basket.getId());
        Set<Long> basketItemIds = basketItems.stream().map(BasketItem::getBasketItemId).collect(Collectors.toSet());
        Set<Long> basketItemId = basket.getBasketItems().stream().map(BasketItem::getBasketItemId).collect(Collectors.toSet());

        basketItemIds.stream().filter(id -> !basketItemId.contains(id)).forEach(id -> basketItemService.deleteById(id));

        basket.getBasketItems().forEach(basketItemService::update);

        logger.info("Basket {} was updated", basket.toString());
        basket = basketRepository.save(basket);
        basket.setBasketItems((List<BasketItem>) basketItemService.findAllByBasketId(basket.getId()));
        return basket;
    }

    public void updateStatus(String status, Long basketId) {
        basketRepository.updateStatus(status, basketId);
    }

    public Optional<Basket> findById(Long basketId) {
        Optional<Basket> basket = basketRepository.findById(basketId);
        basket.ifPresent(basket1 -> basket1.setBasketItems((List<BasketItem>) basketItemService.findAllByBasketId(basketId)));
        return basket;
    }

    @Transactional
    public BigDecimal close(Basket basket) throws BasketStatusException, NotFoundException {
        basket = countTotalPrice(basket);
        this.update(basket);
        this.updateStatus(Basket.BasketStatus.CLOSED.toString(), basket.getId());
        logger.info("Basket {} was closed", basket.toString());
        return basket.getTotalPrice();
    }

    public void deleteById(Long basketId) throws BasketStatusException, NotFoundException {
        Basket basket = findById(basketId).orElseThrow(() -> {
            logger.error("Basket {} not exists", basketId);
            return new NotFoundException("User not found");
        });

        if (basket.getStatus().equals(Basket.BasketStatus.CLOSED)) {
            logger.error("Basket {} has {} status, can not be deleted", basketId, basket.getStatus());
            throw new BasketStatusException(String.format("%s basket can not be deleted", basket.getStatus().toString()));
        } else {
            basketRepository.deleteById(basketId);
        }
    }

    public Basket countTotalPrice(Basket basket) throws NotFoundException {
        basket.setTotalPrice(priceStrategyService.getFinalPrice(basket));
        return basket;
    }

}
