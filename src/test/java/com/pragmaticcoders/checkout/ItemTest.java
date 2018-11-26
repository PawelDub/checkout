package com.pragmaticcoders.checkout;

import com.pragmaticcoders.checkout.model.Item;
import com.pragmaticcoders.checkout.repository.ItemRepository;
import com.pragmaticcoders.checkout.service.ItemService;
import org.checkerframework.checker.units.qual.A;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ItemTest {

    @Autowired
    ItemService itemService;

    @Autowired
    ItemRepository itemRepository;

    @Test
    @DisplayName("item should be add correctly")
    public  void addItem() {

        Item item = new Item("koszule", new BigDecimal(40));
        Item savedItem = itemService.save(item);
        assertThat(savedItem.getType()).isEqualTo(itemRepository.findById(savedItem.getId()).get().getType());
    }

    @Test
    @DisplayName("should update item correctly")
    public  void updateItem() {

        Item item = new Item("koszule", new BigDecimal(40));
        Item savedItem = itemService.save(item);
        assertThat(savedItem.getType()).isEqualTo(item.getType());

        item.setType("spodnie");

        savedItem = itemService.save(item);
        assertThat(savedItem.getType()).isEqualTo(item.getType());
    }

    @Test
    @DisplayName("should update item correctly")
    public  void deleteItem() {

        Item item = new Item("koszule", new BigDecimal(40));
        Item savedItem = itemService.save(item);
        assertThat(savedItem.getType()).isEqualTo(item.getType());

        itemService.delete(savedItem.getId());

        assertThat(itemRepository.findById(savedItem.getId())).isEmpty();
    }

    @Test
    @DisplayName("should find item by type")
    public  void findItemByType() {

        Item item_1 = new Item("koszule", new BigDecimal(40));
        Item item_2 = new Item("spodnie", new BigDecimal(30));
        Item savedItem_1 = itemService.save(item_1);
        Item savedItem_2 = itemService.save(item_2);

        Optional<Item> itemByType = itemService.findItemsByType("koszule");
        assertThat(itemByType.get().getType()).isEqualTo(item_1.getType());

    }
}
