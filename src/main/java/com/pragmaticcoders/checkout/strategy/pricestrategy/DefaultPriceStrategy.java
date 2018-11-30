package com.pragmaticcoders.checkout.strategy.pricestrategy;

import com.pragmaticcoders.checkout.model.Basket;
import com.pragmaticcoders.checkout.model.BasketItem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class DefaultPriceStrategy implements PriceStrategy {

    Logger logger = LogManager.getLogger(DefaultPriceStrategy.class);

    @Override
    public BigDecimal calculationFinalPrice(Basket basket) {
        Double finalPrice = new Double("0.00");
        for (BasketItem basketItem : basket.getBasketItems()) {
            if (basketItem.getItem() != null) {
                finalPrice += basketItem.getItem().getPrice().multiply(BigDecimal.valueOf(basketItem.getQuantity())).setScale(2, RoundingMode.HALF_UP).doubleValue();
                logger.info("finalPrice ============== {} ", finalPrice);
            }
        }

        logger.info("Default price strategy: Final price: {}", finalPrice);
        return new BigDecimal(finalPrice).setScale(2, RoundingMode.HALF_UP);
    }
}
