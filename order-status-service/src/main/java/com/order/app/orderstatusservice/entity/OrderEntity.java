package com.order.app.orderstatusservice.entity;

import com.order.app.orderstatusservice.enums.DecisionStatus;
import com.order.app.orderstatusservice.enums.NotificationStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "order_status")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderEntity {

    @Id
    private String orderId;

    private String product;
    private int qty;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DecisionStatus decisionStatus;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationStatus notificationStatus;
}
