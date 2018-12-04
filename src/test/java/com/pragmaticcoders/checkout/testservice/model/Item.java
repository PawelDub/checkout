package com.pragmaticcoders.checkout.testservice.model;

import com.pragmaticcoders.checkout.model.BasketItem;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

public class Item {
    private Long id;
    private String type;
    private BigDecimal price;
    private Set<BasketItem> basketItems = new HashSet<BasketItem>();

    public Item(Long id, String type, BigDecimal price, Set<BasketItem> basketItems) {
        this.id = id;
        this.type = type;
        this.price = price;
        this.basketItems = basketItems;
    }

    public Item(String type, BigDecimal price, Set<BasketItem> basketItems) {
        this.type = type;
        this.price = price;
        this.basketItems = basketItems;
    }

    public Item(String type, BigDecimal price) {
        this.type = type;
        this.price = price;
    }

    public Item() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Set<BasketItem> getBasketItems() {
        return basketItems;
    }

    public void setBasketItems(Set<BasketItem> basketItems) {
        this.basketItems = basketItems;
    }
}
