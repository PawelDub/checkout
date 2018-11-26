package com.pragmaticcoders.checkout.strategy.pricestrategy;

import com.pragmaticcoders.checkout.model.Basket;
import com.pragmaticcoders.checkout.model.Item;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class DefaultPriceStrategy implements PriceStrategy {

    @Override
    public BigDecimal calculationFinalPrice(Basket basket) {
        Double finalPrice = new Double("0.00");

        for (Item item : basket.getItems()) {
            finalPrice += item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())).setScale(2, RoundingMode.HALF_UP).doubleValue();
        }

        return new BigDecimal(finalPrice).setScale(2, RoundingMode.HALF_UP);
    }
}