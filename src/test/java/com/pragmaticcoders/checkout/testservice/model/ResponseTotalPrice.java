package com.pragmaticcoders.checkout.testservice.model;

import java.math.BigDecimal;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ResponseTotalPrice)) return false;
        ResponseTotalPrice that = (ResponseTotalPrice) o;
        return Objects.equals(getTotalPrice(), that.getTotalPrice());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getTotalPrice());
    }

    @Override
    public String toString() {
        return "ResponseTotalPrice{" +
                "totalPrice=" + totalPrice +
                '}';
    }
}
