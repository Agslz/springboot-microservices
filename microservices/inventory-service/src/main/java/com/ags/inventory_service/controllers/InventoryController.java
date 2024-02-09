package com.ags.inventory_service.controllers;

import com.ags.inventory_service.model.dtos.BaseResponse;
import com.ags.inventory_service.model.dtos.OrderItemRequest;
import com.ags.inventory_service.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping("/{sku}")
    @ResponseStatus(HttpStatus.OK)
    public Boolean isInStock(@PathVariable String sku){
        return inventoryService.isInStock(sku);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse areInStock(@RequestBody List<OrderItemRequest> orderItems){
        return inventoryService.areInStock(orderItems);
    }

}