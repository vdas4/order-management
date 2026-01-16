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

    public void save(Order order, String status) {
        OrderStatusEntity entity = new OrderStatusEntity(
                order.getOrderId(),
                order.getProduct(),
                order.getQty(),
                status
        );
        repo.save(entity);
    }
}