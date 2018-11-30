package com.pragmaticcoders.checkout.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@ApiModel
@Entity
@Table(name = "item")
public class Item {

    @Column(name = "item_id")
    @ApiModelProperty(position = 1, dataType = "Long", required = true, notes = "The database generated product ID")
    @Id
    @SequenceGenerator(name = "SEQ_ITEM", sequenceName = "SEQ_ITEM", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ITEM")
    private Long id;

    @Column(name = "type")
    @NotEmpty(message = "Type can not be empty or null")
    @ApiModelProperty(position = 2, dataType = "String", required = true, notes = "type")
    private String type;

    @Column(name = "price")
    @NotNull(message = "Price can not be empty")
    @ApiModelProperty(position = 3, dataType = "BigDecimal", required = true, notes = "price")
    private BigDecimal price;

    @OneToMany(mappedBy = "item", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @ApiModelProperty(position = 4, dataType = "Set<BasketItem>", required = true, notes = "basket items")
    private Set<BasketItem> basketItems = new HashSet<BasketItem>();

    public Item(@Valid String type, @Valid BigDecimal price) {
        this.type = type;
        this.price = price;
    }

    public Item() {
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", price=" + price +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item)) return false;
        Item item = (Item) o;
        return Objects.equals(getId(), item.getId()) &&
                Objects.equals(getType(), item.getType()) &&
                Objects.equals(getPrice(), item.getPrice());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getId(), getType(), getPrice());
    }
}
