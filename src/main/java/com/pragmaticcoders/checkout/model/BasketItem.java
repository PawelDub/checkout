package com.pragmaticcoders.checkout.model;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "basket_item")
public class BasketItem {

    @ApiModelProperty(position = 1, dataType = "Long", required = true, notes = "The basket Item Id generated product ID")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "basket_item_id")
    private Long basketItemId;

    @ApiModelProperty(position = 2, dataType = "Basket", required = true, notes = "basket")
    @ManyToOne
    @JoinColumn(name = "basket_id")
    private Basket basket;

    @ApiModelProperty(position = 3, dataType = "Item", required = true, notes = "item")
    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    @Column(name = "item_quantity")
    @ApiModelProperty(position = 4, dataType = "Set<BasketItem>", required = true, notes = "basket items")
    private int quantity;

    public BasketItem(Basket basket, Item item, int quantity) {
        this.basket = basket;
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

    public Basket getBasket() {
        return basket;
    }

    public void setBasket(Basket basket) {
        this.basket = basket;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BasketItem)) return false;
        BasketItem that = (BasketItem) o;
        return getQuantity() == that.getQuantity() &&
                Objects.equals(getBasketItemId(), that.getBasketItemId()) &&
                Objects.equals(getBasket(), that.getBasket()) &&
                Objects.equals(getItem(), that.getItem());
    }

}
