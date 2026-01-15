package com.order.app.inventoryservice.service;

import com.order.app.inventoryservice.exception.OutOfStockException;
import com.order.app.inventoryservice.model.Order;
import org.springframework.stereotype.Service;

@Service
public class InventoryService {

    public Order validateOrder(Order order) {
        if(order.getQuantity() > 5) {
            throw new OutOfStockException("Order ID " + order.getOrderId() + " is out of stock for product " + order.getProduct());
        }
        return order;
    }
}
