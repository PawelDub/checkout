package com.pragmaticcoders.checkout.model;

import com.fasterxml.jackson.annotation.JsonProperty;
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
@Table(name = "basket")
@Data
@NoArgsConstructor
public class Basket {

    public enum BasketStatus {
        NEW, ACTIVE, CLOSED, CANCELED
    }

    @ApiModelProperty(position = 1, dataType = "long", required = true, notes = "The database generated product ID")
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long basket_id;

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

    @ManyToMany(mappedBy="basket")
    @JoinTable(
            name = "basket_item",
            joinColumns = { @JoinColumn(name = "basket_id") },
            inverseJoinColumns = { @JoinColumn(name = "item_id") }
    )
    private Set<Item> items = new LinkedHashSet<>();

}
