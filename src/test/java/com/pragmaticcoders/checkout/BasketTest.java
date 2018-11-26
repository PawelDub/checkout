package com.pragmaticcoders.checkout;

import com.pragmaticcoders.checkout.exceptions.BasketStatusException;
import com.pragmaticcoders.checkout.model.Basket;
import com.pragmaticcoders.checkout.model.BasketItem;
import com.pragmaticcoders.checkout.model.Discount;
import com.pragmaticcoders.checkout.model.Item;
import com.pragmaticcoders.checkout.repository.BasketRepository;
import com.pragmaticcoders.checkout.repository.DiscountRepository;
import com.pragmaticcoders.checkout.service.BasketService;
import com.pragmaticcoders.checkout.service.ItemService;
import com.pragmaticcoders.checkout.service.PriceDiscountService;
import com.pragmaticcoders.checkout.service.discount.price.PriceStrategyService;
import com.pragmaticcoders.checkout.service.discount.price.Strategy;
import javassist.NotFoundException;
import org.checkerframework.checker.units.qual.A;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BasketTest {

    @Autowired
    PriceStrategyService priceStrategyService;
    @Autowired
    BasketService basketService;
    @Autowired
    BasketRepository basketRepository;
    @Autowired
    PriceDiscountService priceDiscountService;
    @Autowired
    DiscountRepository discountRepository;
    @Autowired
    ItemService itemService;

    @Test
    @DisplayName("total price should be calculated correctly depends on price strategy")
    public void totalPrice() {

        itemService.save(new Item("koszule", new BigDecimal(40)));
        itemService.save(new Item("spodnie", new BigDecimal(10)));
        itemService.save(new Item("buty", new BigDecimal(30)));
        itemService.save(new Item("rekawiczki", new BigDecimal(25)));
        itemService.save(new Item("kurtki", new BigDecimal(45)));
        itemService.save(new Item("palta", new BigDecimal(80)));

        priceDiscountService.save(new Discount("koszule", 3, new BigDecimal(70)));
        priceDiscountService.save(new Discount("spodnie", 2, new BigDecimal(15)));
        priceDiscountService.save(new Discount("buty", 4, new BigDecimal(60)));
        priceDiscountService.save(new Discount("rekawiczki", 2, new BigDecimal(40)));

        Basket basket_1 = basketService.open();

        List<Item> items = (List<Item>) itemService.findAll();

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


            basketService.findById(basket.getId());
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
    @DisplayName("should not found basket")
    public void notFoundBasket() {
        Throwable exception = assertThrows(NotFoundException.class, () -> {
            basketService.delete(5454654654L);
        });

        assertThat(exception.getMessage()).isEqualTo("User not found");
    }


}
