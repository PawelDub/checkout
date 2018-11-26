package com.pragmaticcoders.checkout.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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

    @Column(name = "totalPrice")
    @NotNull(message = "Total price can not be empty")
    @ApiModelProperty(position = 3, dataType = "BigDecimal", required = true, notes = "Total price")
    @JsonProperty(value = "total_price")
    private BigDecimal totalPrice;

    @ManyToMany(
            fetch = FetchType.EAGER,
            cascade = {CascadeType.ALL})
    @JoinTable(name = "basket_item",
            joinColumns = {@JoinColumn(name = "basket_id")},
            inverseJoinColumns = {@JoinColumn(name = "item_id")}
    )
    private List<Item> items = new ArrayList<>();

    public Basket(@Valid BasketStatus status, @Valid BigDecimal totalPrice) {
        this.status = status;
        this.totalPrice = totalPrice;
    }

    public Basket(@Valid BasketStatus status, @Valid BigDecimal totalPrice, List<Item> items) {
        this.status = status;
        this.totalPrice = totalPrice;
        this.items = items;
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

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "Basket{" +
                "id=" + id +
                ", status=" + status +
                ", totalPrice=" + totalPrice +
                ", items=" + items +
                '}';
    }
}
