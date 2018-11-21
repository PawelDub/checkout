package com.pragmaticcoders.checkout.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Set;

@ApiModel
@Entity
@Table(name = "baskets")
public class Basket {

    @ApiModelProperty(position = 1, required = true, notes = "The database generated product ID")
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;


    @Column(name = "status")
    @NotNull(message = "Status can not be empty")
    @ApiModelProperty(position = 3, required = true, notes = "basket status")
    private BasketStatus status;

    @Column(name = "totalPrice")
    @NotNull(message = "Total price can not be empty")
    @ApiModelProperty(position = 3, required = true, notes = "Total price")
    private BigDecimal totalPrice;

    @OneToMany(mappedBy="basket")
    private Set<Item> items;

}
