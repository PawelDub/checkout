package com.pragmaticcoders.checkout.testservice.service;

import com.pragmaticcoders.checkout.testservice.model.Basket;
import com.pragmaticcoders.checkout.testservice.model.ResponseTotalPrice;
import com.pragmaticcoders.checkout.testservice.specification.SpecBuilder;

import static io.restassured.RestAssured.given;

public class BasketService {

    private static final String BASKET_OPEN = "/basket/open";
    private static final String BASKET_FIND_BY_ID = "/basket/{id}";
    private static final String BASKET_UPDATE = "/basket/update";
    private static final String BASKET_CLOSE = "/basket/close";

    public static Basket openNewBasket() {
        return given()
                .spec(SpecBuilder.requestSpecBuilder)
                .when()
                .get(BASKET_OPEN)
                .andReturn()
                .then()
                .statusCode(200)
                .extract()
                .as(Basket.class);
    }

    public static Basket findBasketById(Long id) {
        return given()
                .spec(SpecBuilder.requestSpecBuilder)
                .when()
                .get(BASKET_FIND_BY_ID, id)
                .andReturn()
                .then()
                .statusCode(200)
                .extract()
                .as(Basket.class);
    }

    public static Basket updateBasket(Basket basket) {
        return given()
                .spec(SpecBuilder.requestSpecBuilder)
                .when()
                .body(basket)
                .put(BASKET_UPDATE)
                .andReturn()
                .then()
                .statusCode(200)
                .extract()
                .as(Basket.class);
    }

    public static ResponseTotalPrice closeBasket(Basket basket) {
        return given()
                .spec(SpecBuilder.requestSpecBuilder)
                .when()
                .body(basket)
                .post(BASKET_CLOSE)
                .andReturn()
                .then()
                .statusCode(200)
                .extract()
                .as(ResponseTotalPrice.class);
    }
}
