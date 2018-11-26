package com.pragmaticcoders.checkout.controllers;

import com.pragmaticcoders.checkout.service.discount.price.Strategy;
import com.pragmaticcoders.checkout.service.discount.price.PriceStrategyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/strategy/price")
public class PriceStrategyController {

    //TODO logger
    //TODO exceptions

    private PriceStrategyService priceStrategyService;

    @Autowired
    public PriceStrategyController(PriceStrategyService priceStrategyService) {
        this.priceStrategyService = priceStrategyService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public void setStrategy(@RequestParam Strategy name) {
        priceStrategyService.setPriceStrategy(name);
    }

    @RequestMapping(method = RequestMethod.GET)
    public Strategy setStrategy() {
       return priceStrategyService.getStrategy();
    }
}
