package com.pragmaticcoders.checkout.strategy.pricestrategy;

import com.pragmaticcoders.checkout.model.Basket;

import java.math.BigDecimal;

public interface PriceStrategy {
    BigDecimal calculationFinalPrice(Basket basket);
}
