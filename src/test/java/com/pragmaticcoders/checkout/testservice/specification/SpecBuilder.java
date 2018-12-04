package com.pragmaticcoders.checkout.testservice.specification;

import com.pragmaticcoders.checkout.testservice.config.TestConfig;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class SpecBuilder {
    public static RequestSpecification requestSpecBuilder = new RequestSpecBuilder()
            .setContentType(ContentType.JSON)
            .setBaseUri(TestConfig.baseUri)
            .build();
}
