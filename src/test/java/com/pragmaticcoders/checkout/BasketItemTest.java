package com.pragmaticcoders.checkout;

import com.pragmaticcoders.checkout.exceptions.BasketStatusException;
import com.pragmaticcoders.checkout.model.Basket;
import com.pragmaticcoders.checkout.model.BasketItem;
import com.pragmaticcoders.checkout.model.Item;
import com.pragmaticcoders.checkout.service.BasketItemService;
import com.pragmaticcoders.checkout.service.BasketService;
import com.pragmaticcoders.checkout.service.ItemService;
import javassist.NotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static com.google.common.truth.Truth.assertThat;

@DisplayName("Basket item tests")
@SpringBootTest
public class BasketItemTest {

    @Autowired
    BasketService basketService;

    @Autowired
    ItemService itemService;

    @Autowired
    BasketItemService basketItemService;

    @Test
    @DisplayName("should save basket item correctly")
    public void saveBasketItem() {

        Basket basket = basketService.open();

        Item item = new Item("koszule", new BigDecimal(40));
        itemService.save(item);

        BasketItem basketItem = new BasketItem(basket.getId(), item, 8);
        BasketItem basketItemResponse = basketItemService.save(basketItem);

        assertThat(basketItemResponse.getBasketId()).isEqualTo(basket.getId());
        assertThat(basketItemResponse.getItem().getId()).isEqualTo(item.getId());
        assertThat(basketItemResponse.getQuantity()).isEqualTo(basketItem.getQuantity());

        basketItemService.deleteById(basketItem.getBasketItemId());
        itemService.deleteById(item.getId());

        try {
            basketService.deleteById(basket.getId());
        } catch (BasketStatusException e) {
            e.printStackTrace();
        } catch (NotFoundException e) {
            e.printStackTrace();
        }

    }

    @Test
    @DisplayName("should update basket item correctly")
    public void updateBasketItem() {

        Basket basket = basketService.open();

        Item item = new Item("koszule", new BigDecimal(40));
        itemService.save(item);

        BasketItem basketItem = new BasketItem(basket.getId(), item, 8);

        BasketItem basketItemResponse = basketItemService.save(basketItem);

        assertThat(basketItemResponse.getQuantity()).isEqualTo(8);

        basketItem.setQuantity(5);
        basketItemResponse = basketItemService.save(basketItem);

        assertThat(basketItemResponse.getQuantity()).isEqualTo(5);

        basketItemService.deleteById(basketItem.getBasketItemId());
        itemService.deleteById(item.getId());

        try {
            basketService.deleteById(basket.getId());
        } catch (BasketStatusException e) {
            e.printStackTrace();
        } catch (NotFoundException e) {
            e.printStackTrace();
        }

    }

    @Test
    @DisplayName("should remove basket item correctly")
    public void removeBasketItem() {

        Basket basket = basketService.open();

        Item item_1 = new Item("koszule", new BigDecimal(40.235));
        Item item_2 = new Item("spodnie", new BigDecimal(300.02));
        itemService.save(item_1);
        itemService.save(item_2);

        BasketItem basketItem_1 = new BasketItem(basket.getId(), item_1, 8);
        BasketItem basketItem_2 = new BasketItem(basket.getId(), item_2, 8);
        basketItemService.save(basketItem_1);
        basketItemService.save(basketItem_2);

        List<BasketItem> basketItemList = (List<BasketItem>) basketItemService.findAllByBasketId(basket.getId());

        assertThat(basketItemList.size()).isEqualTo(2);

        basketItemService.deleteById(basketItemList.get(0).getBasketItemId());

        basketItemList = (List<BasketItem>) basketItemService.findAllByBasketId(basket.getId());

        assertThat(basketItemList.size()).isEqualTo(1);

        basketItemService.deleteById(basketItem_2.getBasketItemId());
        itemService.deleteById(item_1.getId());
        itemService.deleteById(item_2.getId());

        try {
            basketService.deleteById(basket.getId());
        } catch (BasketStatusException e) {
            e.printStackTrace();
        } catch (NotFoundException e) {
            e.printStackTrace();
        }

    }

    @Test
    @DisplayName("should find by basket id and item id")
    public void findByBasketIdAndItemId() {

        Basket basket = basketService.open();

        Item item_1 = new Item("koszule", new BigDecimal(40.23));
        Item item_2 = new Item("spodnie", new BigDecimal(30.02));
        item_1 = itemService.save(item_1);
        item_2 = itemService.save(item_2);

        BasketItem basketItem_1 = new BasketItem(basket.getId(), item_1, 8);
        BasketItem basketItem_2 = new BasketItem(basket.getId(), item_2, 5);
        basketItemService.save(basketItem_1);
        basketItemService.save(basketItem_2);

        Optional<BasketItem> basketItem = basketItemService.findByBasketIdAndItemId(basket.getId(), item_1.getId());

        assertThat(basketItem.get().getBasketId()).isEqualTo(basket.getId());
        assertThat(basketItem.get().getItem().getId()).isEqualTo(item_1.getId());

        basketItemService.deleteById(basketItem_1.getBasketItemId());
        basketItemService.deleteById(basketItem_2.getBasketItemId());
        itemService.deleteById(item_1.getId());
        itemService.deleteById(item_2.getId());

        try {
            basketService.deleteById(basket.getId());
        } catch (BasketStatusException e) {
            e.printStackTrace();
        } catch (NotFoundException e) {
            e.printStackTrace();
        }

    }

}