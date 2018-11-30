package com.pragmaticcoders.checkout.strategy.pricestrategy;

import com.pragmaticcoders.checkout.model.Basket;
import com.pragmaticcoders.checkout.model.BasketItem;
import com.pragmaticcoders.checkout.model.Item;
import com.pragmaticcoders.checkout.model.PriceDiscount;
import com.pragmaticcoders.checkout.service.PriceDiscountService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

@Service
public class PricePerTypeStrategy implements PriceStrategy {

    Logger logger = LogManager.getLogger(PricePerTypeStrategy.class);

    private PriceDiscountService priceDiscountService;

    @Autowired
    public PricePerTypeStrategy(PriceDiscountService priceDiscountService) {
        this.priceDiscountService = priceDiscountService;
    }

    @Override
    public BigDecimal calculationFinalPrice(Basket basket) {
        Map<String, PriceDiscount> discountMap = priceDiscountService.getDiscountAsMap();
        Double finalPrice = new Double("0.00");

        for (BasketItem basketItem : basket.getBasketItems()) {
            Item item = basketItem.getItem();
            if (discountMap.containsKey(item.getType()) && basketItem.getQuantity() >= discountMap.get(item.getType()).getQuantity()) {
                int quantityDiscounts = basketItem.getQuantity() / discountMap.get(item.getType()).getQuantity();
                int quantityNotDiscounted = basketItem.getQuantity() % discountMap.get(item.getType()).getQuantity();

                finalPrice += discountMap.get(item.getType()).getPriceDiscount().multiply(BigDecimal.valueOf(quantityDiscounts)).setScale(2, RoundingMode.HALF_UP).doubleValue();

                if (quantityNotDiscounted != 0) {
                    finalPrice += item.getPrice().multiply(BigDecimal.valueOf(quantityNotDiscounted)).setScale(2, RoundingMode.HALF_UP).doubleValue();
                }
            } else {
                finalPrice += item.getPrice().multiply(BigDecimal.valueOf(basketItem.getQuantity())).setScale(2, RoundingMode.HALF_UP).doubleValue();
            }
        }

        logger.info("Price per type strategy: Final price: {}", finalPrice);
        return new BigDecimal(finalPrice).setScale(2, RoundingMode.HALF_UP);
    }

}