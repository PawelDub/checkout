package com.pragmaticcoders.checkout.service;

import com.pragmaticcoders.checkout.model.BasketItem;
import com.pragmaticcoders.checkout.repository.BasketItemRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BasketItemService {

    private BasketItemRepository basketItemRepository;

    @Autowired
    public BasketItemService(BasketItemRepository basketItemRepository) {
        this.basketItemRepository = basketItemRepository;
    }

    public BasketItem save(BasketItem basketItem) {
        return basketItemRepository.save(basketItem);
    }

    public BasketItem update(BasketItem basketItem) {
        return basketItemRepository.save(basketItem);
    }

    public void delete(BasketItem basketItem) {
        basketItemRepository.delete(basketItem);
    }

    public Iterable<BasketItem> findAllByBasketId(Long basketId) throws NotFoundException {
        return basketItemRepository.findAllByBasketId(basketId);
    }
}
