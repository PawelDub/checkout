package com.pragmaticcoders.checkout;

import com.pragmaticcoders.checkout.model.Item;
import com.pragmaticcoders.checkout.repository.ItemRepository;
import com.pragmaticcoders.checkout.service.ItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Item tests")
@SpringBootTest
public class ItemTest {

    @Autowired
    ItemService itemService;

    @Autowired
    ItemRepository itemRepository;

    @BeforeEach
    public void before() {
        itemRepository.deleteAll();
    }

    @Test
    @DisplayName("item should be add correctly")
    public  void addItem() {

        Item item = new Item("koszule", new BigDecimal(40));
        Item savedItem = itemService.save(item);
        assertThat(savedItem.getType()).isEqualTo(itemRepository.findById(savedItem.getId()).get().getType());
        itemService.deleteById(item.getId());
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
        itemService.deleteById(item.getId());
    }

    @Test
    @DisplayName("should deleteById item correctly")
    public  void deleteItem() {

        Item item = new Item("koszule", new BigDecimal(40));
        Item savedItem = itemService.save(item);
        assertThat(savedItem.getType()).isEqualTo(item.getType());

        itemService.deleteById(savedItem.getId());

        assertThat(itemRepository.findById(savedItem.getId())).isEmpty();
    }

    @Test
    @DisplayName("should find item by type")
    public  void findItemByType() {

        Item item_1 = new Item("koszule", new BigDecimal(40));
        Item item_2 = new Item("spodnie", new BigDecimal(30));
        itemService.save(item_1);
        itemService.save(item_2);

        Item itemByType = itemService.findItemByType("koszule").get();
        assertThat(itemByType.getType()).isEqualTo(item_1.getType());
        itemService.deleteById(item_1.getId());
        itemService.deleteById(item_2.getId());

    }
}
