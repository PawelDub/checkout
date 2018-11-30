package com.pragmaticcoders.checkout.controllers;

import com.pragmaticcoders.checkout.model.PriceDiscount;
import com.pragmaticcoders.checkout.service.PriceDiscountService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(value = "Price Discount", description = "Price discount manage operations")
@ApiResponses(value = {
        @ApiResponse(code = 400, message = "Requests param are incorrect"),
        @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
        @ApiResponse(code = 500, message = "Internal Server Error")
})
@RestController
@RequestMapping("/discount/price")
public class PriceDiscountController {

    //TODO exceptions handler

    private PriceDiscountService priceDiscountService;

    @Autowired
    public PriceDiscountController(PriceDiscountService priceDiscountService) {
        this.priceDiscountService = priceDiscountService;
    }

    @ApiOperation(value = "Save new price discount")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully save new price discount"),
    })
    @RequestMapping(method = RequestMethod.POST)
    public PriceDiscount saveItem(@RequestBody @Valid PriceDiscount priceDiscount) {
        return priceDiscountService.save(priceDiscount);
    }

    @ApiOperation(value = "Update price discount")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully update price discount"),
    })
    @RequestMapping(method = RequestMethod.PUT)
    public PriceDiscount updateItem(@RequestBody @Valid PriceDiscount priceDiscount) {
        return priceDiscountService.update(priceDiscount);
    }

    @ApiOperation(value = "Delete price discount")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully deleteById price discount"),
    })
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteItem(@ApiParam("Id of the price discount to be obtained. Cannot be empty.")
            @PathVariable Long id) {
        priceDiscountService.deleteById(id);
    }

    @ApiOperation(value = "Get all price discounts")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully returns all price discounts"),
    })
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public Iterable<PriceDiscount> findAllItem() {
        return priceDiscountService.findAll();
    }

}
