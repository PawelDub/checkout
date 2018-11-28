package com.pragmaticcoders.checkout.service.discount.price;

import com.pragmaticcoders.checkout.model.Basket;
import com.pragmaticcoders.checkout.strategy.pricestrategy.DefaultPriceStrategy;
import com.pragmaticcoders.checkout.strategy.pricestrategy.PriceStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PriceStrategyService {

    //TODO logger

    private Strategy strategy = Strategy.DEFAULT;

    private PriceStrategy priceStrategy = new DefaultPriceStrategy();
    private PriceStrategy defaultPriceStrategy;
    private PriceStrategy pricePerTypeStrategy;

    @Autowired
    public PriceStrategyService(PriceStrategy defaultPriceStrategy, PriceStrategy pricePerTypeStrategy) {
        this.defaultPriceStrategy = defaultPriceStrategy;
        this.pricePerTypeStrategy = pricePerTypeStrategy;
    }

    public void setPriceStrategy(Strategy strategy) {
        setStrategy(strategy);

        switch (this.strategy) {
            case DEFAULT:
                setPriceStrategy(defaultPriceStrategy);
                break;
            case PRICE_PER_TYPE:
                setPriceStrategy(pricePerTypeStrategy);
                break;
        }
    }

    public BigDecimal getFinalPrice(Basket basket) {
        BigDecimal calculationPrice = priceStrategy.calculationFinalPrice(basket);
        //TODO logger
        return calculationPrice;
    }

    public Strategy getStrategy() {
        return strategy;
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    public PriceStrategy getPriceStrategy() {
        return priceStrategy;
    }

    private void setPriceStrategy(PriceStrategy priceStrategy) {
        this.priceStrategy = priceStrategy;
    }
}