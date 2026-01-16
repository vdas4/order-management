package com.order.app.orderstatusservice.entity;


import ch.qos.logback.core.joran.spi.NoAutoStart;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Entity
@Table(name = "order_status")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderStatusEntity {
    @Id
    @Column(name = "order_id", nullable = false)
    private String orderId;

    @Column(name = "status", nullable = false)
    private String status;

    @Lob
    @Column(name = "last_event_payload", nullable = false)
    private String lastEventPayload;

    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;
}