package com.order.app.notificationservice.service;

import com.order.app.notificationservice.model.Order;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    public void send(Order order) {
        // Simulate sending a notification (e.g., email, SMS)
        System.out.println("Notification sent for Order ID: " + order.getOrderId() +
                           ", Product: " + order.getProduct() +
                           ", Quantity: " + order.getQuantity());
    }
}
