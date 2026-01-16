package com.order.app.orderstatusservice.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.order.app.orderstatusservice.entity.OrderStatusEntity;
import com.order.app.orderstatusservice.repo.OrderStatusRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

@Service
public class OrderStatusService {

    private final OrderStatusRepository repo;
    private final ObjectMapper mapper = new ObjectMapper();

    public OrderStatusService(OrderStatusRepository repo) {
        this.repo = repo;
    }

    @Transactional
    public void applyEvent(String eventJson) throws Exception {
        JsonNode node = mapper.readTree(eventJson);
        String eventType = node.get("eventType").asText();
        String orderId = node.get("orderId").asText();

        String status = switch (eventType) {
            case "OrderCreated" -> "CREATED";
            case "OrderValidated" -> node.path("data").path("valid").asBoolean(false) ? "VALIDATED" : "REJECTED";
            case "OrderNotificationSent" -> "NOTIFIED";
            default -> "UNKNOWN";
        };

        OrderStatusEntity rec = repo.findById(orderId).orElseGet(OrderStatusEntity::new);
        rec.setOrderId(orderId);
        rec.setStatus(status);
        rec.setLastEventPayload(eventJson);
        rec.setUpdatedAt(OffsetDateTime.now());

        repo.save(rec);
    }
}
