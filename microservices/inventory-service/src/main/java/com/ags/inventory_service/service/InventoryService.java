package com.ags.inventory_service.service;

import com.ags.inventory_service.model.dtos.BaseResponse;
import com.ags.inventory_service.model.dtos.OrderItemRequest;
import com.ags.inventory_service.model.entities.Inventory;
import com.ags.inventory_service.repositories.InventoryRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    public Boolean isInStock(String sku) {
        var inventory = inventoryRepository.findBySku(sku);
        return inventory.map(value -> value.getQuantity() > 0).orElse(false);
    }

    public BaseResponse areInStock(List<OrderItemRequest> orderItems) {
        List<String> errorList = new ArrayList<>();
        List<String> skus = orderItems.stream().map(OrderItemRequest::getSku).toList();
        List<Inventory> inventoryList = inventoryRepository.findBySkuIn(skus);

        orderItems.forEach(orderItem -> {
            validateOrderItem(orderItem, inventoryList, errorList);
        });

        return errorList.isEmpty() ? new BaseResponse(null) : new BaseResponse(errorList.toArray(new String[0]));
    }

    private void validateOrderItem(OrderItemRequest orderItem, List<Inventory> inventoryList, List<String> errorList) {
        var inventory = inventoryList.stream().filter(value -> value.getSku().equals(orderItem.getSku())).findFirst();
        if (inventory.isEmpty()) {
            errorList.add("Product with sku " + orderItem.getSku() + " does not exist");
        } else if (inventory.get().getQuantity() < orderItem.getQuantity()) {
            errorList.add("Product with sku " + orderItem.getSku() + " has insufficient quantity");
        }
    }
}

