package com.pragmaticcoders.checkout.strategy.pricestrategy;

import com.pragmaticcoders.checkout.model.Basket;
import com.pragmaticcoders.checkout.model.Discount;
import com.pragmaticcoders.checkout.model.Item;
import com.pragmaticcoders.checkout.service.PriceDiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

@Service
public class SpecialPricePerTypeStrategy implements PriceStrategy {

    private PriceDiscountService priceDiscountService;

    @Autowired
    public SpecialPricePerTypeStrategy(PriceDiscountService priceDiscountService) {
        this.priceDiscountService = priceDiscountService;
    }

    @Override
    public BigDecimal calculationFinalPrice(Basket basket) {
        Map<String, Discount> discountMap = priceDiscountService.getDiscountAsMap();
        Double totalPrice = new Double("0.00");

        for (Item item : basket.getItems()) {
            if (discountMap.containsKey(item.getType()) && item.getQuantity() >= discountMap.get(item.getType()).getQuantity()) {
                int quantityDiscounts = item.getQuantity() / discountMap.get(item.getType()).getQuantity();
                int quantityNotDiscounted = item.getQuantity() % discountMap.get(item.getType()).getQuantity();

                totalPrice += discountMap.get(item.getType()).getDiscountPrice().multiply(BigDecimal.valueOf(quantityDiscounts)).setScale(2, RoundingMode.HALF_UP).doubleValue();

                if (quantityNotDiscounted != 0) {
                    totalPrice += item.getPrice().multiply(BigDecimal.valueOf(quantityNotDiscounted)).setScale(2, RoundingMode.HALF_UP).doubleValue();
                }
            } else {
                totalPrice += item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())).setScale(2, RoundingMode.HALF_UP).doubleValue();
            }
        }

        return new BigDecimal(totalPrice).setScale(2, RoundingMode.HALF_UP);
    }

}