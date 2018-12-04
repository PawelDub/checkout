package com.pragmaticcoders.checkout.test.integrationtest;

import com.pragmaticcoders.checkout.model.BasketStatus;
import com.pragmaticcoders.checkout.model.PriceDiscount;
import com.pragmaticcoders.checkout.repository.PriceDiscountRepository;

import com.pragmaticcoders.checkout.service.PriceDiscountService;
import com.pragmaticcoders.checkout.service.discount.price.PriceStrategyService;
import com.pragmaticcoders.checkout.testservice.model.BasketItem;
import com.pragmaticcoders.checkout.testservice.model.Item;
import com.pragmaticcoders.checkout.testservice.service.BasketService;
import com.pragmaticcoders.checkout.testservice.model.Basket;
import com.pragmaticcoders.checkout.testservice.model.ResponseTotalPrice;
import com.pragmaticcoders.checkout.testservice.service.ItemService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BasketTestINT {
    @Autowired
    PriceStrategyService priceStrategyService;
    @Autowired
    PriceDiscountService priceDiscountService;
    @Autowired
    PriceDiscountRepository priceDiscountRepository;

    @Test
    @DisplayName("should return new basket correctly")
    public void getNewBasket() {

        Basket basket = BasketService.openNewBasket();
        assertThat(basket.getStatus()).isEqualTo(BasketStatus.NEW);
        assertThat(basket.getTotalPrice()).isEqualTo(new BigDecimal("0.00"));
        assertThat(basket.getBasketItems()).isEmpty();
    }

    @Test
    @DisplayName("should find basket by id")
    public void findBasketById() {

        Basket basket = BasketService.openNewBasket();

        Basket basketFind = BasketService.findBasketById(basket.getId());
        assertThat(basket.getStatus()).isEqualTo(basketFind.getStatus());
        assertThat(basket.getTotalPrice()).isEqualTo(basketFind.getTotalPrice());
        assertThat(basket.getBasketItems()).isEmpty();
    }

    @Test
    @DisplayName("should update basket correctly")
    public void updateBasket() {

        Basket basket = BasketService.openNewBasket();
        basket.setTotalPrice(new BigDecimal("10.00"));

        Basket basketUpdated = BasketService.updateBasket(basket);
        assertThat(basketUpdated.getStatus()).isEqualTo(BasketStatus.ACTIVE);
        assertThat(basketUpdated.getTotalPrice()).isEqualTo(basket.getTotalPrice());
    }

    @Test
    @DisplayName("should close and count basket correctly")
    public void closeBasket() {

        List<Item> items = new ArrayList<>();

        items.add(new Item("koszule", new BigDecimal(40)));
        items.add(new Item("spodnie", new BigDecimal(10)));

        items.forEach(ItemService::addItem);


        List<PriceDiscount> discounts = new ArrayList<>();
        discounts.add(new PriceDiscount("koszule", 3, new BigDecimal(70)));
        discounts.add(new PriceDiscount("spodnie", 2, new BigDecimal(15)));

        discounts.forEach(discount -> priceDiscountService.save(discount));

        Basket basket = BasketService.openNewBasket();

        items = ItemService.getAllItems();
        System.out.println("items: " + items);

        BasketItem basketItem_1 = new BasketItem();
        basketItem_1.setItem(items.get(0));
        basketItem_1.setBasketId(basket.getId());
        basketItem_1.setQuantity(8);                // ( 70 * 2 ) + ( 2 * 40 ) = 220    // default 8 * 40 = 320

        BasketItem basketItem_2 = new BasketItem();
        basketItem_2.setItem(items.get(1));
        basketItem_2.setBasketId(basket.getId());
        basketItem_2.setQuantity(2);                // ( 1 * 15 ) = 15                 // default 2 * 10 = 20
        //-------------------------------------------------------------------------------------------------------
        //                                             total = 245                     // default total: 340

        basket.setBasketItems(Arrays.asList(basketItem_1, basketItem_2));
        System.out.println("Basket: " + basket);

        ResponseTotalPrice totalPrice = BasketService.closeBasket(basket);

        Assertions.assertThat(totalPrice.getTotalPrice()).isEqualTo(new BigDecimal("340.00"));

        items.forEach(item -> {
            ItemService.deleteItem(item.getId());
        });

        discounts.forEach(discount -> {
            priceDiscountRepository.deleteById(discount.getId());
        });

    }
}
