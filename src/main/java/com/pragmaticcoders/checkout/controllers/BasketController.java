package com.pragmaticcoders.checkout.controllers;

import com.pragmaticcoders.checkout.exceptions.BasketStatusException;
import com.pragmaticcoders.checkout.model.Basket;
import com.pragmaticcoders.checkout.service.BasketService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;

@Api(value = "Basket Controller", basePath = "/basket", description = "Basket manage operations")
@ApiResponses(value = {
        @ApiResponse(code = 400, message = "Requests param are incorrect"),
        @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
        @ApiResponse(code = 500, message = "Internal Server Error")
})
@RestController
@RequestMapping("/basket")
public class BasketController {

    //TODO exceptions handler

    private BasketService basketService;

    public BasketController() {
    }

    @Autowired
    public BasketController(BasketService basketService) {
        this.basketService = basketService;
    }

    @ApiOperation(value = "Open new basket")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully open new basket", response = Basket.class),
    })
    @RequestMapping(value = "/open", method = RequestMethod.GET)
    public Basket openBasket() {
        return basketService.open();
    }

    @ApiOperation(value = "Get basket")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully return the basket", response = Basket.class),
    })
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Basket findById(@PathVariable Long id) {
        return basketService.findById(id).get();
    }

    @ApiOperation(value = "Update basket")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully update basket", response = Basket.class),
    })
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public Basket updateBasket(@RequestBody @Valid Basket basket) throws BasketStatusException {
        return basketService.update(basket);
    }

    @ApiOperation(value = "Close basket")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully close basket", response = BigDecimal.class),
    })
    @RequestMapping(value = "/close", method = RequestMethod.POST)
    public BigDecimal closeBasket(@RequestBody @Valid Basket basket) throws BasketStatusException, NotFoundException {
        return basketService.close(basket);
    }

}
