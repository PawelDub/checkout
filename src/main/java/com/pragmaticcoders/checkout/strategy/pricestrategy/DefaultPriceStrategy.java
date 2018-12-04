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
        final Double[] finalPrice = {new Double("0.00")};

        basket.getBasketItems().stream().filter(basketItem -> basketItem.getItem() != null).forEach(basketItem -> {
            finalPrice[0] += basketItem.getItem().getPrice().multiply(BigDecimal.valueOf(basketItem.getQuantity())).setScale(2, RoundingMode.HALF_UP).doubleValue();
            logger.info("finalPrice ============== {} ", finalPrice[0]);
        });

        logger.info("Default price strategy: Final price: {}", finalPrice[0]);
        return new BigDecimal(finalPrice[0]).setScale(2, RoundingMode.HALF_UP);
    }
}
