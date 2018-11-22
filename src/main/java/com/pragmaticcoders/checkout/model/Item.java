package com.pragmaticcoders.checkout.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

@ApiModel
@Entity
@Table(name = "item")
@Data
@NoArgsConstructor
public class Item {

    @ApiModelProperty(position = 1, dataType = "Long", required = true, notes = "The database generated product ID")
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @Column(name = "type")
    @NotNull(message = "Type can not be empty or null")
    @ApiModelProperty(position = 2, dataType = "String", required = true, notes = "type")
    private String productType;

    @Column(name = "price")
    @NotNull(message = "Price can not be empty")
    @ApiModelProperty(position = 3, dataType = "BigDecimal", required = true, notes = "price")
    private BigDecimal price;

    @Column(name = "quantity")
    @ApiModelProperty(position = 4, dataType = "int", required = true, notes = "quantity")
    private int quantity;

    @ManyToMany(mappedBy = "items")
    @JoinColumn(name="id", nullable=false)
    private Set<Basket> baskets = new LinkedHashSet<>();

}
