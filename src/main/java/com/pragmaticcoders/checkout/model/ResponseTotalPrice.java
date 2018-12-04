package com.pragmaticcoders.checkout.model;

import java.math.BigDecimal;

public class ResponseTotalPrice {

    private BigDecimal totalPrice;

    public ResponseTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public ResponseTotalPrice() {
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}
