package com.pragmaticcoders.checkout.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@ApiModel
@Entity
@Table(name = "items")
public class Item {

    @ApiModelProperty(position = 1, required = true, notes = "The database generated product ID")
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @Column(name = "type")
    @NotNull(message = "Type can not be empty or null")
    @ApiModelProperty(position = 2, required = true, notes = "type")
    private String productType;

    @Column(name = "price")
    @NotNull(message = "Price can not be empty")
    @ApiModelProperty(position = 3, required = true, notes = "price")
    private String price;

    @Column(name = "quantity")
    @NotNull(message = "Quantity can not be empty")
    @ApiModelProperty(position = 4, required = true, notes = "qauntity")
    private int quantity;

    @ManyToOne
    @JoinColumn(name="id", nullable=false)
    private Basket basket;

}
