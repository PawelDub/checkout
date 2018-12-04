package com.pragmaticcoders.checkout.test.integrationtest;

import com.pragmaticcoders.checkout.testservice.model.Item;
import com.pragmaticcoders.checkout.testservice.service.ItemService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;

@DisplayName("Items controller")
public class ItemTestINT {

    @Test
    @DisplayName("should return item by type")
    public void itemByType() {
        List<Item> items = new ArrayList<>();

        Item item_1 = new Item("koszule", new BigDecimal(40));
        Item item_2 = new Item("spodnie", new BigDecimal(10));

        Item item_A = ItemService.addItem(item_1);
        Item item_B = ItemService.addItem(item_2);

        Item item = ItemService.getItemByType("koszule");

        assertThat(item.getType()).isEqualTo("koszule");
        assertThat(item.getPrice()).isEqualTo(new BigDecimal("40.00"));

        ItemService.deleteItem(item_A.getId());
        ItemService.deleteItem(item_B.getId());
    }

    @Test
    @DisplayName("should save item correctly")
    public void saveItem() {

        Item item = new Item("koszule", new BigDecimal("40.00"));

        Item itemNew = ItemService.addItem(item);

        assertThat(itemNew.getType()).isEqualTo(item.getType());
        assertThat(itemNew.getPrice()).isEqualTo(item.getPrice());

        ItemService.deleteItem(itemNew.getId());

    }

    @Test
    @DisplayName("should update item correctly")
    public void updateItem() {

        Item item = new Item("koszule", new BigDecimal("40.00"));

        Item itemNew = ItemService.addItem(item);

        itemNew.setType("spodnie");
        itemNew.setPrice(new BigDecimal(10));

        Item updatedItem = ItemService.updateItem(itemNew);

        assertThat(updatedItem.getType()).isEqualTo(itemNew.getType());
        assertThat(updatedItem.getPrice()).isEqualTo(itemNew.getPrice());

        ItemService.deleteItem(updatedItem.getId());

    }

    @Test
    @DisplayName("should find all items")
    public void findAllItems() {
        Item item_1 = new Item("koszule", new BigDecimal(40));
        Item item_2 = new Item("spodnie", new BigDecimal(10));

        Item item_A = ItemService.addItem(item_1);
        Item item_B = ItemService.addItem(item_2);

        List<Item> items = ItemService.getAllItems();

        assertThat(items.get(0).getType()).isEqualTo("koszule");
        assertThat(items.get(0).getPrice()).isEqualTo(new BigDecimal("40.00"));
        assertThat(items.get(1).getType()).isEqualTo("spodnie");
        assertThat(items.get(1).getPrice()).isEqualTo(new BigDecimal("10.00"));

        items.forEach(item -> ItemService.deleteItem(item.getId()));

    }
}
