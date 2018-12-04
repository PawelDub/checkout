package com.pragmaticcoders.checkout.testservice.model;

public class BasketItem {

    private Long basketItemId;
    private Long basketId;
    private Item item;
    private int quantity;

    public BasketItem(Long basketId, Item item, int quantity) {
        this.basketId = basketId;
        this.item = item;
        this.quantity = quantity;
    }

    public BasketItem() {
    }

    public Long getBasketItemId() {
        return basketItemId;
    }

    public void setBasketItemId(Long basketItemId) {
        this.basketItemId = basketItemId;
    }

    public Long getBasketId() {
        return basketId;
    }

    public void setBasketId(Long basketId) {
        this.basketId = basketId;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
