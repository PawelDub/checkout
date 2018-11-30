package com.pragmaticcoders.checkout.service;

import com.pragmaticcoders.checkout.model.BasketItem;
import com.pragmaticcoders.checkout.repository.BasketItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

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

    public void deleteById(Long basketItemId) {
        basketItemRepository.deleteById(basketItemId);
    }

    public Collection<BasketItem> findAllByBasketId(Long basketId) {
        return basketItemRepository.findAllByBasketId(basketId);
    }

    public Optional<BasketItem> findByBasketIdAndItemId(Long basketId, Long itemId) {
        return basketItemRepository.findByBasketIdAndItemId(basketId, itemId);
    }
}
