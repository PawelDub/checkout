package com.pragmaticcoders.checkout.strategy.pricestrategy;

import com.pragmaticcoders.checkout.model.Basket;
import javassist.NotFoundException;

import java.math.BigDecimal;

public interface PriceStrategy {
    BigDecimal calculationFinalPrice(Basket basket) throws NotFoundException;
}
