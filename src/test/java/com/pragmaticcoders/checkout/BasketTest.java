package com.pragmaticcoders.checkout;

import com.pragmaticcoders.checkout.exceptions.BasketStatusException;
import com.pragmaticcoders.checkout.model.Basket;
import com.pragmaticcoders.checkout.model.BasketItem;
import com.pragmaticcoders.checkout.model.Item;
import com.pragmaticcoders.checkout.model.PriceDiscount;
import com.pragmaticcoders.checkout.repository.PriceDiscountRepository;
import com.pragmaticcoders.checkout.service.BasketItemService;
import com.pragmaticcoders.checkout.service.BasketService;
import com.pragmaticcoders.checkout.service.ItemService;
import com.pragmaticcoders.checkout.service.PriceDiscountService;
import com.pragmaticcoders.checkout.service.discount.price.PriceStrategyService;
import com.pragmaticcoders.checkout.service.discount.price.Strategy;
import javassist.NotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class BasketTest {

    @Autowired
    PriceStrategyService priceStrategyService;
    @Autowired
    BasketService basketService;
    @Autowired
    PriceDiscountService priceDiscountService;
    @Autowired
    PriceDiscountRepository priceDiscountRepository;
    @Autowired
    ItemService itemService;

    @Autowired
    BasketItemService basketItemService;

    @Test
    @DisplayName("total price should be calculated correctly depends on price strategy")
    public void totalPrice() {

        List<Item> items = new ArrayList<>();

        items.add(new Item("koszule", new BigDecimal(40)));
        items.add(new Item("spodnie", new BigDecimal(10)));
        items.add(new Item("buty", new BigDecimal(30)));
        items.add(new Item("rekawiczki", new BigDecimal(25)));
        items.add(new Item("kurtki", new BigDecimal(45)));
        items.add(new Item("palta", new BigDecimal(80)));

        items.forEach(item -> itemService.save(item));

        List<PriceDiscount> discounts = new ArrayList<>();
        discounts.add(new PriceDiscount("koszule", 3, new BigDecimal(70)));
        discounts.add(new PriceDiscount("spodnie", 2, new BigDecimal(15)));
        discounts.add(new PriceDiscount("buty", 4, new BigDecimal(60)));
        discounts.add(new PriceDiscount("rekawiczki", 2, new BigDecimal(40)));

        discounts.forEach(discount -> priceDiscountService.save(discount));

        Basket basket_1 = basketService.open();

        items = (List<Item>) itemService.findAll();

        BasketItem basketItem_1 = new BasketItem();
        basketItem_1.setItem(items.get(0));
        basketItem_1.setQuantity(8);                // ( 70 * 2 ) + ( 2 * 40 ) = 220    // default 8 * 40 = 320

        BasketItem basketItem_2 = new BasketItem();
        basketItem_2.setItem(items.get(1));
        basketItem_2.setQuantity(2);                // ( 1 * 15 ) = 15                 // default 2 * 10 = 20

        BasketItem basketItem_3 = new BasketItem();
        basketItem_3.setItem(items.get(2));
        basketItem_3.setQuantity(3);                //  3 * 30 = 90                    // dafault 3 * 30 = 90

        BasketItem basketItem_4 = new BasketItem();
        basketItem_4.setItem(items.get(5));
        basketItem_4.setQuantity(2);                // 2 * 80 = 160                    // default 2 * 80 = 160
        //--------------------------------------------------------
        // total = 485            // default total: 590
        basket_1.addBasketItem(basketItem_1);
        basket_1.addBasketItem(basketItem_2);
        basket_1.addBasketItem(basketItem_3);
        basket_1.addBasketItem(basketItem_4);

        BigDecimal totalPrice = basketService.countTotalPrice(basket_1).getTotalPrice();

        assertThat(totalPrice).isEqualTo(new BigDecimal("590.00"));

        priceStrategyService.setPriceStrategy(Strategy.PRICE_PER_TYPE);

        BigDecimal totalPricePerType = basketService.countTotalPrice(basket_1).getTotalPrice();
        assertThat(totalPricePerType).isEqualTo(new BigDecimal("485.00"));

        priceStrategyService.setPriceStrategy(Strategy.DEFAULT);

        BigDecimal totalPriceDefault = basketService.countTotalPrice(basket_1).getTotalPrice();
        assertThat(totalPriceDefault).isEqualTo(new BigDecimal("590.00"));

        items.forEach(item -> itemService.delete(item.getId()));
        discounts.forEach(discount -> priceDiscountService.delete(discount));

        items.forEach(item -> {
            itemService.delete(item.getId());
        });

        discounts.forEach(discount -> {
            priceDiscountRepository.deleteById(discount.getId());
        });

        try {
            basketService.delete(basket_1.getId());
        } catch (BasketStatusException e) {
            e.printStackTrace();
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("should update basket correctly")
    public void updateBasket() {
        Item item_1 = new Item("koszule", new BigDecimal(40));
        Item item_2 = new Item("spodnie", new BigDecimal(10));
        itemService.save(item_1);
        itemService.save(item_2);

        Basket basket = basketService.open();

        BasketItem basketItem_1 = new BasketItem(basket, item_1, 3);
        basket.addBasketItem(basketItem_1);

        try {
            basket = basketService.update(basket);
        } catch (BasketStatusException e) {
            e.printStackTrace();
        }

        List<BasketItem> basketItems = (List<BasketItem>) basketItemService.findAllByBasketId(basket.getId());

        assertThat(basketItems.size()).isEqualTo(1);
        assertThat(basket.getBasketItems().size()).isEqualTo(1);
        assertThat(basketItems.get(0).getBasket().getId()).isEqualTo(basket.getId());
        assertThat(basketItems.get(0).getItem().getId()).isEqualTo(item_1.getId());
        assertThat(basketItems.get(0).getQuantity()).isEqualTo(3);

        BasketItem basketItem_2 = new BasketItem(basket, item_2, 2);

        Set<BasketItem> basketItemSet = new HashSet<>();

        basketItemSet.add(basketItems.get(0));
        basketItemSet.add(basketItem_2);

        basket.setBasketItems(basketItemSet);

        assertThat(basket.getBasketItems().size()).isEqualTo(2);

        try {
            basket = basketService.update(basket);
        } catch (BasketStatusException e) {
            e.printStackTrace();
        }

        basketItems = (List<BasketItem>) basketItemService.findAllByBasketId(basket.getId());

        assertThat(basketItems.size()).isEqualTo(2);
        assertThat(basketItems.get(0).getBasket().getId()).isEqualTo(basket.getId());
        assertThat(basketItems.get(0).getItem().getId()).isEqualTo(item_1.getId());
        assertThat(basketItems.get(0).getQuantity()).isEqualTo(3);
        assertThat(basketItems.get(1).getBasket().getId()).isEqualTo(basket.getId());
        assertThat(basketItems.get(1).getItem().getId()).isEqualTo(item_2.getId());
        assertThat(basketItems.get(1).getQuantity()).isEqualTo(2);


        basketItem_1 = basketItems.get(0);
        basketItem_2 = basketItems.get(1);
        basketItem_1.setQuantity(8);
        basketItem_2.setQuantity(5);

        basketItemSet = new HashSet<>();
        basketItemSet.add(basketItem_1);
        basketItemSet.add(basketItem_2);

        basket.setBasketItems(basketItemSet);

        try {
            basket = basketService.update(basket);
        } catch (BasketStatusException e) {
            e.printStackTrace();
        }

        basketItems = (List<BasketItem>) basketItemService.findAllByBasketId(basket.getId());

        assertThat(basketItems.size()).isEqualTo(2);
        assertThat(basketItems.get(0).getBasket().getId()).isEqualTo(basket.getId());
        assertThat(basketItems.get(0).getItem().getId()).isEqualTo(item_1.getId());
        assertThat(basketItems.get(0).getQuantity()).isEqualTo(8);
        assertThat(basketItems.get(1).getBasket().getId()).isEqualTo(basket.getId());
        assertThat(basketItems.get(1).getItem().getId()).isEqualTo(item_2.getId());
        assertThat(basketItems.get(1).getQuantity()).isEqualTo(5);

        basketItemSet.remove(basketItem_1);

        basket.setBasketItems(basketItemSet);

        try {
            basket = basketService.update(basket);
        } catch (BasketStatusException e) {
            e.printStackTrace();
        }

        basketItems = (List<BasketItem>) basketItemService.findAllByBasketId(basket.getId());

        assertThat(basketItems.size()).isEqualTo(1);
        assertThat(basketItems.get(0).getBasket().getId()).isEqualTo(basket.getId());
        assertThat(basketItems.get(0).getItem().getId()).isEqualTo(item_2.getId());
        assertThat(basketItems.get(0).getItem().getType()).isEqualTo(item_2.getType());
        assertThat(basketItems.get(0).getQuantity()).isEqualTo(5);

        basketItemService.deleteById(basketItems.get(0).getBasketItemId());
        itemService.delete(item_1.getId());
        itemService.delete(item_2.getId());

        try {
            basketService.delete(basket.getId());
        } catch (BasketStatusException e) {
            e.printStackTrace();
        } catch (NotFoundException e) {
            e.printStackTrace();
        }

    }

    @Test
    @DisplayName("should open new basket correctly")
    public void saveBasket() {
        Basket basket = basketService.open();
        assertThat(basket.getStatus()).isEqualTo(Basket.BasketStatus.NEW);
        assertThat(basket.getTotalPrice()).isEqualTo(new BigDecimal("0.00"));
        assertThat(basket.getBasketItems()).isEmpty();
    }

    @DisplayName("should delete basket correctly")
    @ParameterizedTest(name = "should delete {0} basket correctly")
    @EnumSource(value = Basket.BasketStatus.class, names = {"NEW", "ACTIVE", "CANCELED"})
    public void deleteBasket(Basket.BasketStatus status) {
        Basket basket = basketService.open();
        basket.setStatus(status);

        assertThat(basket.getStatus()).isEqualTo(status);

        try {
            basketService.delete(basket.getId());
        } catch (BasketStatusException e) {
            e.printStackTrace();
        } catch (NotFoundException e) {
            e.printStackTrace();
        }

        assertThat(basketService.findById(basket.getId())).isEmpty();

    }

    @Test
    @DisplayName("should not delete CLOSED basket")
    public void deleteClosedBasket() {
        Basket basket = basketService.open();
        basket = basketService.close(basket);

        assertThat(basket.getStatus()).isEqualTo(Basket.BasketStatus.CLOSED);

        Basket finalBasket = basket;
        Throwable exception = assertThrows(BasketStatusException.class, () -> {
            basketService.delete(finalBasket.getId());
        });

        assertThat(exception.getMessage()).isEqualTo("CLOSED basket can not be deleted");

    }

    @Test
    @DisplayName("should not found not existed basket")
    public void notFoundBasket() {
        Throwable exception = assertThrows(NotFoundException.class, () -> {
            basketService.delete(5454654654L);
        });

        assertThat(exception.getMessage()).isEqualTo("User not found");
    }

}
