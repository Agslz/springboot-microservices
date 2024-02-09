package com.ags.orders_service.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemRequest {

    private Long id;
    private String sku;
    private Double price;
    private Long quantity;

}
