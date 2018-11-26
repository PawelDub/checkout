package com.pragmaticcoders.checkout;

import com.pragmaticcoders.checkout.model.Basket;
import com.pragmaticcoders.checkout.model.Item;
import com.pragmaticcoders.checkout.repository.DiscountRepository;
import com.pragmaticcoders.checkout.service.BasketService;
import com.pragmaticcoders.checkout.service.ItemService;
import com.pragmaticcoders.checkout.service.PriceDiscountService;
import com.pragmaticcoders.checkout.service.discount.price.PriceStrategyService;
import com.pragmaticcoders.checkout.service.discount.price.Strategy;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CheckoutApplicationTests {

    @Autowired
    PriceStrategyService priceStrategyService;

    @Autowired
    BasketService basketService;

    @Autowired
    PriceDiscountService priceDiscountService;

    @Autowired
    DiscountRepository discountRepository;

    @Autowired
    ItemService itemService;

    @Test
    public void totalPrice() {

        Basket basket_1 = basketService.open();

        List<Item> items = (List<Item>) itemService.findAll();

        List<Item> basket_1_items = new ArrayList<>();

        Item item1 = items.get(0);
        item1.setQuantity(8);  // ( 70 * 2 ) + ( 2 * 40 ) = 220    // default 8 * 40 = 320

        Item item2 = items.get(1);
        item2.setQuantity(2);  // ( 1 * 15 ) = 15                 // default 2 * 10 = 20

        Item item3 = items.get(2);
        item3.setQuantity(3);  //  3 * 30 = 90                    // dafault 3 * 30 = 90

        Item item4 = items.get(5);
        item4.setQuantity(2);  // 2 * 80 = 160                    // default 2 * 80 = 160
                                        // total = 485            // default total: 590
        basket_1_items.add(item1);
        basket_1_items.add(item2);
        basket_1_items.add(item3);
        basket_1_items.add(item4);

        basket_1.setItems(basket_1_items);

        BigDecimal totalPrice = basketService.countTotalPrice(basket_1).getTotalPrice();

        assertThat(totalPrice).isEqualTo(new BigDecimal("590.00"));

        priceStrategyService.setPriceStrategy(Strategy.PRICE_PER_TYPE);

        BigDecimal totalPricePerType = basketService.countTotalPrice(basket_1).getTotalPrice();
        assertThat(totalPricePerType).isEqualTo(new BigDecimal("485.00"));

        priceStrategyService.setPriceStrategy(Strategy.DEFAULT);

        BigDecimal totalPriceDefault = basketService.countTotalPrice(basket_1).getTotalPrice();
        assertThat(totalPriceDefault).isEqualTo(new BigDecimal("590.00"));
    }

}
