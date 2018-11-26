package com.pragmaticcoders.checkout.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Table(name = "discount")
@ApiModel
public class Discount {

    @Column(name = "discount_id")
    @ApiModelProperty(position = 1, dataType = "Long", required = true, notes = "The database generated product ID")
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @Column(name = "type")
    @NotNull(message = "Type can not be empty")
    @ApiModelProperty(position = 2, dataType = "String", required = true, notes = "item type")
    private String type;

    @Column(name = "quantity")
    @NotNull(message = "Type can not be empty")
    @ApiModelProperty(position = 3, dataType = "String", required = true, notes = "item type")
    private int quantity;

    @Column(name = "discount_price")
    @NotNull(message = "Discount price can not be empty")
    @ApiModelProperty(position = 4, dataType = "BigDecimal", required = true, notes = "Discount price")
    private BigDecimal discountPrice;

    public Discount(@Valid String type, @Valid int quantity, @Valid BigDecimal discountPrice) {
        this.type = type;
        this.quantity = quantity;
        this.discountPrice = discountPrice;
    }

    public Discount() {
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

    public BigDecimal getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(BigDecimal discountPrice) {
        this.discountPrice = discountPrice;
    }

    @Override
    public String toString() {
        return "Discount{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", quantity=" + quantity +
                ", discountPrice=" + discountPrice +
                '}';
    }
}
