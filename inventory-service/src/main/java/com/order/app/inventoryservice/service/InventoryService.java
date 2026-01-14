package com.order.app.inventoryservice.service;

import com.order.app.inventoryservice.model.Order;
import org.springframework.stereotype.Service;

@Service
public class InventoryService {

    public Order validateOrder(Order order) {
        if(order.getOrderId() == null || order.getQuantity() > 5) {
            throw new RuntimeException("Order quantity exceeded");
        }
        return order;
    }
}
