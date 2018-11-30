package com.pragmaticcoders.checkout.controllers;

import com.pragmaticcoders.checkout.model.Item;
import com.pragmaticcoders.checkout.service.ItemService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Api(value = "Item", description = "Items manage operations")
@ApiResponses(value = {
        @ApiResponse(code = 400, message = "Requests param are incorrect"),
        @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
        @ApiResponse(code = 500, message = "Internal Server Error")
})
@RestController
@RequestMapping("/item")
public class ItemController {

    //TODO exceptions handler

    private ItemService itemService;

    public ItemController() {
    }

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @ApiOperation(value = "Get item by type")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully return searched item"),
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "Item's type", required = true, dataType = "string", paramType = "query")
    })
    @RequestMapping(method = RequestMethod.GET)
    public Optional<Item> getItemByType(@RequestParam(value = "type") String type) {
        return itemService.findItemByType(type);
    }

    @ApiOperation(value = "Save item")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully save new item"),
    })
    @RequestMapping(method = RequestMethod.POST)
    public Item saveItem(@RequestBody @Valid Item item) {
        return itemService.save(item);
    }

    @ApiOperation(value = "Update item")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully update searched item"),
    })
    @RequestMapping(method = RequestMethod.PUT)
    public Item updateItem(@RequestBody @Valid Item item) {
        return itemService.update(item);
    }

    @ApiOperation(value = "Delete item by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully deleteById searched item"),
    })
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void saveItem(@ApiParam("Id of the item to be obtained. Cannot be empty.")
            @PathVariable Long id) {
        itemService.deleteById(id);
    }

    @ApiOperation(value = "Find all items")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully find all items"),
    })
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public Iterable<Item> findAll() {
        return itemService.findAll();
    }

}