package com.pragmaticcoders.checkout.model;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "basket_item")
public class BasketItem {

    @ApiModelProperty(position = 1, dataType = "Long", required = true, notes = "The basket Item Id generated product ID")
    @Id
    @SequenceGenerator(name = "SEQ_BASKET_ITEM", sequenceName = "SEQ_BASKET_ITEM", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_BASKET_ITEM")
    @Column(name = "basket_item_id")
    private Long basketItemId;

    @ApiModelProperty(position = 2, dataType = "Basket", required = true, notes = "basket")
    @Column(name = "basket_id")
    private Long basketId;

    @ApiModelProperty(position = 3, dataType = "Item", required = true, notes = "item")
    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    @Column(name = "item_quantity")
    @ApiModelProperty(position = 4, dataType = "Set<BasketItem>", required = true, notes = "basket items")
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

    @Override
    public String toString() {
        return "BasketItem{" +
                "basketItemId=" + basketItemId +
                ", basketId=" + basketId +
                ", item=" + item +
                ", quantity=" + quantity +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BasketItem)) return false;
        BasketItem that = (BasketItem) o;
        return getQuantity() == that.getQuantity() &&
                Objects.equals(getBasketItemId(), that.getBasketItemId()) &&
                Objects.equals(getBasketId(), that.getBasketId()) &&
                Objects.equals(getItem(), that.getItem());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getBasketItemId(), getBasketId(), getItem(), getQuantity());
    }
}
