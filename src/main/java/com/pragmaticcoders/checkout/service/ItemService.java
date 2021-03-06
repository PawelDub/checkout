package com.pragmaticcoders.checkout.service;

import com.pragmaticcoders.checkout.model.Item;
import com.pragmaticcoders.checkout.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ItemService  {

    private ItemRepository itemRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public Item save(Item item) {
        return itemRepository.save(item);
    }

    public Item update(Item item) {
        return itemRepository.save(item);
    }

    public void deleteById(Long itemId) {
        itemRepository.deleteById(itemId);
    }

    public Optional<Item> findItemByType(String type) {
        return itemRepository.findItemByType(type);
    }

    public Iterable<Item> findAll() {
        return itemRepository.findAll();
    }

}
