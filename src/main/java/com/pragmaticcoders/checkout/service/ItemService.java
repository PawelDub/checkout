package com.pragmaticcoders.checkout.service;

import com.pragmaticcoders.checkout.model.Item;
import com.pragmaticcoders.checkout.repository.ItemRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
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

    public void delete(Long itemId) {
        itemRepository.deleteById(itemId);
    }

    public Collection<Item> findItemsByType(String type) throws NotFoundException {
        return itemRepository.findItemsByType(type);
    }
}
