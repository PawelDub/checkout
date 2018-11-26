package com.pragmaticcoders.checkout.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@ApiModel
@Entity
@Table(name = "basket")
public class Basket {

    public enum BasketStatus {
        NEW, ACTIVE, CLOSED, CANCELED
    }

    @Column(name = "basket_id")
    @ApiModelProperty(position = 1, dataType = "Long", required = true, notes = "The database generated product ID")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    @OneToMany(mappedBy = "basket", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @ApiModelProperty(position = 3, dataType = "Set<BasketItem>", required = true, notes = "basket items")
    private Set<BasketItem> basketItems = new HashSet<BasketItem>();

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

    public Set<BasketItem> getBasketItems() {
        return basketItems;
    }

    public void setBasketItems(Set<BasketItem> basketItems) {
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
}
