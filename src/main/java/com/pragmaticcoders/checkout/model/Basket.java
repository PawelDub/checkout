package com.pragmaticcoders.checkout.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.*;

@ApiModel
@Entity
@Table(name = "basket")
public class Basket {

    @Column(name = "basket_id")
    @ApiModelProperty(position = 1, dataType = "Long", required = true, notes = "The database generated product ID")
    @Id
    @SequenceGenerator(name = "SEQ_BASKET", sequenceName = "SEQ_BASKET", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_BASKET")
    private Long id;

    @Column(name = "status")
    @NotNull(message = "Status can not be empty")
    @Enumerated(EnumType.STRING)
    @ApiModelProperty(position = 2, dataType = "BasketStatus", allowableValues = "NEW, ACTIVE, CLOSED, CANCELED", required = true, notes = "basket status")
    private BasketStatus status;

    @Column(name = "total_price")
    @NotNull(message = "Total price can not be empty")
    @ApiModelProperty(position = 3, dataType = "BigDecimal", required = true, notes = "Total price")
    private BigDecimal totalPrice;

    @Transient
    @ApiModelProperty(position = 4, dataType = "Set<BasketItem>", required = true, notes = "basket items")
    private List<BasketItem> basketItems = new ArrayList<>();

    public Basket(@Valid BasketStatus status, @Valid BigDecimal totalPrice) {
        this.status = status;
        this.totalPrice = totalPrice;
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

    public void addBasketItem(BasketItem basketItem) {
        this.basketItems.add(basketItem);
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
}
