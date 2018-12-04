package com.pragmaticcoders.checkout.testservice.service;

import com.pragmaticcoders.checkout.testservice.model.Item;
import com.pragmaticcoders.checkout.testservice.specification.SpecBuilder;

import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;

public class ItemService {

    private static final String ITEM = "/item";
    private static final String ITEM_BY_ID = "/item/{id}";
    private static final String ITEM_ALL = "/item/all";

    public static Item getItemByType(String type) {
        return given()
                .spec(SpecBuilder.requestSpecBuilder)
                .when()
                .queryParams("type", type)
                .get(ITEM)
                .andReturn()
                .then()
                .statusCode(200)
                .extract()
                .as(Item.class);
    }

    public static Item addItem(Item item) {
        return given()
                .spec(SpecBuilder.requestSpecBuilder)
                .when()
                .body(item)
                .post(ITEM)
                .andReturn()
                .then()
                .statusCode(200)
                .extract()
                .as(Item.class);
    }

    public static Item updateItem(Item item) {
        return given()
                .spec(SpecBuilder.requestSpecBuilder)
                .when()
                .body(item)
                .put(ITEM)
                .andReturn()
                .then()
                .statusCode(200)
                .extract()
                .as(Item.class);
    }

    public static void deleteItem(Long id) {
        given()
                .spec(SpecBuilder.requestSpecBuilder)
                .when()
                .delete(ITEM_BY_ID, id)
                .then()
                .statusCode(200);
    }

    public static List<Item> getAllItems() {
        return Arrays.asList(given()
                .spec(SpecBuilder.requestSpecBuilder)
                .when()
                .get(ITEM_ALL)
                .andReturn()
                .then()
                .statusCode(200)
                .extract()
                .as(Item[].class));
    }
}
