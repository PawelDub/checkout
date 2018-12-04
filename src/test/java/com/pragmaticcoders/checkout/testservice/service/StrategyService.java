package com.pragmaticcoders.checkout.testservice.service;

import com.pragmaticcoders.checkout.service.discount.price.Strategy;
import com.pragmaticcoders.checkout.testservice.specification.SpecBuilder;

import static io.restassured.RestAssured.given;

public class StrategyService {

    private static final String PRICE_STRATEGY = "/strategy/price";

    public static Strategy getPriceStrategy() {
        return given()
                .spec(SpecBuilder.requestSpecBuilder)
                .when()
                .get(PRICE_STRATEGY)
                .andReturn()
                .then()
                .statusCode(200)
                .extract()
                .as(Strategy.class);
    }

    public static void setPriceStrategy(Strategy strategy) {
            given()
                .spec(SpecBuilder.requestSpecBuilder)
                .when()
                .queryParams("strategy", strategy)
                .post(PRICE_STRATEGY)
                .andReturn()
                .then()
                .statusCode(200);
    }

}
