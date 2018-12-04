package com.pragmaticcoders.checkout.testservice.config;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class TestConfig {

    public static final Config CONFIG = ConfigFactory.load("test-configuration.conf");
    public static final String ENVIRONMENT = CONFIG.getString("environment");
    public static final Config ENV = CONFIG.getConfig("environments").getConfig(ENVIRONMENT);

    public static final String baseUri = ENV.getString("base-uri");

}
