package com.pragmaticcoders.checkout.configuration;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

@Configuration
public class RequestLoggingFilterConfig {

    @Bean
    public CommonsRequestLoggingFilter logFilter() {
        CommonsRequestLoggingFilter filter
                = new CommonsRequestLoggingFilter();
        filter.setIncludeQueryString(true);
        filter.setIncludePayload(true);
        filter.setMaxPayloadLength(10000);
        filter.setIncludeHeaders(false);
        filter.setAfterMessagePrefix("REQUEST DATA : ");
        return filter;
    }

    @Bean
    public FilterRegistrationBean loggingFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean(logFilter());
        registration.addUrlPatterns("/basket/**");
        registration.addUrlPatterns("/item/**");
        registration.addUrlPatterns("/discount/price/**");
        registration.addUrlPatterns("/strategy/price/**");
        registration.addUrlPatterns("/basket-item/**");
        return registration;
    }
}
