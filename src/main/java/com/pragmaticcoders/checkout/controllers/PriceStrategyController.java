package com.pragmaticcoders.checkout.controllers;

import com.pragmaticcoders.checkout.service.discount.price.Strategy;
import com.pragmaticcoders.checkout.service.discount.price.PriceStrategyService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "Price strategy", description = "Price strategy manage operations")
@ApiResponses(value = {
        @ApiResponse(code = 400, message = "Requests param are incorrect"),
        @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
        @ApiResponse(code = 500, message = "Internal Server Error")
})
@RestController
@RequestMapping("/strategy/price")
public class PriceStrategyController {

    private PriceStrategyService priceStrategyService;

    @Autowired
    public PriceStrategyController(PriceStrategyService priceStrategyService) {
        this.priceStrategyService = priceStrategyService;
    }

    @ApiOperation(value = "Set price strategy")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully set price strategy"),
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "strategy", value = "DEFAULT, PRICE_PER_TYPE;", required = true, dataType = "Strategy", paramType = "query")
    })
    @RequestMapping(method = RequestMethod.POST)
    public void setStrategy(@RequestParam(value = "strategy") Strategy name) {
        priceStrategyService.setPriceStrategy(name);
    }

    @ApiOperation(value = "Get price strategy")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully get actual price strategy"),
    })
    @RequestMapping(method = RequestMethod.GET)
    public Strategy setStrategy() {
       return priceStrategyService.getStrategy();
    }
}
