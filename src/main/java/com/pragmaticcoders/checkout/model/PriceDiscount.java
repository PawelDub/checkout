package com.pragmaticcoders.checkout.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "price_discount")
@ApiModel
public class PriceDiscount {

    @Column(name = "discount_id")
    @ApiModelProperty(position = 1, dataType = "Long", required = true, notes = "The database generated product ID")
    @Id
    @SequenceGenerator(name = "SEQ_PRICE_DISCOUNT", sequenceName = "SEQ_PRICE_DISCOUNT", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PRICE_DISCOUNT")
    private Long id;

    @Column(name = "type")
    @NotNull(message = "Type can not be empty")
    @ApiModelProperty(position = 2, dataType = "String", required = true, notes = "item type")
    private String type;

    @Column(name = "quantity")
    @NotNull(message = "Type can not be empty")
    @ApiModelProperty(position = 3, dataType = "String", required = true, notes = "item type")
    private int quantity;

    @Column(name = "price_discount")
    @NotNull(message = "PriceDiscount price can not be empty")
    @ApiModelProperty(position = 4, dataType = "BigDecimal", required = true, notes = "PriceDiscount price")
    private BigDecimal priceDiscount;

    public PriceDiscount(@Valid String type, @Valid int quantity, @Valid BigDecimal priceDiscount) {
        this.type = type;
        this.quantity = quantity;
        this.priceDiscount = priceDiscount;
    }

    public PriceDiscount() {
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPriceDiscount() {
        return priceDiscount;
    }

    public void setPriceDiscount(BigDecimal priceDiscount) {
        this.priceDiscount = priceDiscount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PriceDiscount)) return false;
        PriceDiscount discount = (PriceDiscount) o;
        return getQuantity() == discount.getQuantity() &&
                Objects.equals(getId(), discount.getId()) &&
                Objects.equals(getType(), discount.getType()) &&
                Objects.equals(getPriceDiscount(), discount.getPriceDiscount());
    }

    @Override
    public String toString() {
        return "PriceDiscount{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", quantity=" + quantity +
                ", priceDiscount=" + priceDiscount +
                '}';
    }
}
