package com.order.app.orderstatusservice.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.order.app.orderstatusservice.entity.OrderStatusEntity;
import com.order.app.orderstatusservice.model.Order;
import com.order.app.orderstatusservice.repo.OrderStatusRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

@Service
public class OrderStatusService {

    private final OrderStatusRepository repo;

    public OrderStatusService(OrderStatusRepository repo) {
        this.repo = repo;
    }

    @Transactional
    public void saveOrUpdate(Order order, String status) {

        String orderId = order.getOrderId().trim();

        int updated = repo.updateStatus(
                orderId,
                order.getProduct(),
                order.getQuantity(),
                status
        );

        if(updated == 0) {
            OrderStatusEntity entity = new OrderStatusEntity();
            entity.setOrderId(orderId);
            entity.setProduct(order.getProduct());
            entity.setQuantity(order.getQuantity());
            entity.setStatus(status);
            repo.save(entity);
        }
    }
}