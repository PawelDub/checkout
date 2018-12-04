package com.pragmaticcoders.checkout.testservice.model;

import com.pragmaticcoders.checkout.model.BasketStatus;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Basket {

    private Long id;
    private BasketStatus status;
    private BigDecimal totalPrice;
    private List<BasketItem> basketItems = new ArrayList<>();

    public Basket(BasketStatus status, BigDecimal totalPrice, List<BasketItem> basketItems) {
        this.status = status;
        this.totalPrice = totalPrice;
        this.basketItems = basketItems;
    }

    public Basket() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BasketStatus getStatus() {
        return status;
    }

    public void setStatus(BasketStatus status) {
        this.status = status;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<BasketItem> getBasketItems() {
        return basketItems;
    }

    public void setBasketItems(List<BasketItem> basketItems) {
        this.basketItems = basketItems;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Basket)) return false;
        Basket basket = (Basket) o;
        return Objects.equals(getId(), basket.getId()) &&
                getStatus() == basket.getStatus() &&
                Objects.equals(getTotalPrice(), basket.getTotalPrice()) &&
                Objects.equals(getBasketItems(), basket.getBasketItems());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getId(), getStatus(), getTotalPrice(), getBasketItems());
    }

    @Override
    public String toString() {
        return "Basket{" +
                "id=" + id +
                ", status=" + status +
                ", totalPrice=" + totalPrice +
                ", basketItems=" + basketItems +
                '}';
    }
}
