package com.pragmaticcoders.checkout.test.integrationtest;

import com.pragmaticcoders.checkout.service.discount.price.Strategy;
import com.pragmaticcoders.checkout.testservice.service.StrategyService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;

@DisplayName("Price strategy")
public class PriceStrategyTestINT {

    @Test
    @DisplayName("get strategy correctly")
    public void getStrategy() {

        Strategy strategy = StrategyService.getPriceStrategy();
        assertThat(strategy).isEqualTo(Strategy.DEFAULT);
    }

    @Test
    @DisplayName("set strategy correctly")
    public void setStrategy() {

        Strategy strategy = StrategyService.getPriceStrategy();
        assertThat(strategy).isEqualTo(Strategy.DEFAULT);

        StrategyService.setPriceStrategy(Strategy.PRICE_PER_TYPE);
        assertThat(StrategyService.getPriceStrategy()).isEqualTo(Strategy.PRICE_PER_TYPE);
        StrategyService.setPriceStrategy(Strategy.DEFAULT);
    }
}
