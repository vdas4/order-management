package com.order.app.orderstatusservice.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.order.app.orderstatusservice.entity.OrderEntity;
import com.order.app.orderstatusservice.enums.DecisionStatus;
import com.order.app.orderstatusservice.enums.NotificationStatus;
import com.order.app.orderstatusservice.model.Order;
import com.order.app.orderstatusservice.repo.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
@Service
public class OrderStatusService {

    private final OrderRepository repo;

    public OrderStatusService(OrderRepository repo) {
        this.repo = repo;
    }

    /* -------- Business decision events -------- */

    @Transactional
    public void onValidated(Order order) {
        saveDecision(order, DecisionStatus.VALIDATED);
    }

    @Transactional
    public void onRejected(Order order) {
        saveDecision(order, DecisionStatus.REJECTED);
    }

    private void saveDecision(Order order, DecisionStatus decision) {
        repo.save(new OrderEntity(
                order.getOrderId(),
                order.getProduct(),
                order.getQuantity(),
                decision,
                NotificationStatus.NOT_SENT
        ));
    }

    /* -------- Notification lifecycle events -------- */

    @Transactional
    public void onNotificationSent(Order order) {
        repo.updateNotificationStatus(
                order.getOrderId(),
                NotificationStatus.SENT
        );
    }

    @Transactional
    public void onNotificationFailed(Order order) {
        repo.updateNotificationStatus(
                order.getOrderId(),
                NotificationStatus.FAILED
        );
    }
}
