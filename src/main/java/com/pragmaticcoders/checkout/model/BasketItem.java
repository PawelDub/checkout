package com.pragmaticcoders.checkout.model;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

@Entity
@Table(name = "basket-item")
public class BasketItem {

    @ApiModelProperty(position = 1, required = true, notes = "The database generated product ID")
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    private Item item;

    private int quantity;


}
