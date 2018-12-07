package com.pragmaticcoders.checkout.service.discount.price;

import com.pragmaticcoders.checkout.model.Basket;
import com.pragmaticcoders.checkout.strategy.pricestrategy.DefaultPriceStrategy;
import com.pragmaticcoders.checkout.strategy.pricestrategy.PriceStrategy;
import javassist.NotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PriceStrategyService {

    Logger logger = LogManager.getLogger(PriceStrategyService.class);

    private Strategy strategy = Strategy.DEFAULT;

    private PriceStrategy priceStrategy = new DefaultPriceStrategy();

    List<PriceStrategy> priceStrategies;

    @Autowired
    public PriceStrategyService(List<PriceStrategy> priceStrategies) {
        this.priceStrategies = priceStrategies;
    }

    public void setPriceStrategy(Strategy strategy) {
        setStrategy(strategy);

        switch (this.strategy) {
            case DEFAULT:
                setPriceStrategy(getPriceStrategie("DefaultPriceStrategy"));
                logger.info("Strategy {} was set", strategy);
                break;
            case PRICE_PER_TYPE:
                setPriceStrategy(getPriceStrategie("PricePerTypeStrategy"));
                logger.info("Strategy {} was set", strategy);
                break;
        }
    }

    private PriceStrategy getPriceStrategie(String strategy) {
        return priceStrategies.stream().filter(strat -> strat.getClass().getSimpleName().equals(strategy)).findAny().orElseThrow(IllegalArgumentException::new);
    }

    public BigDecimal getFinalPrice(Basket basket) throws NotFoundException {
        BigDecimal calculationPrice = priceStrategy.calculationFinalPrice(basket);
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