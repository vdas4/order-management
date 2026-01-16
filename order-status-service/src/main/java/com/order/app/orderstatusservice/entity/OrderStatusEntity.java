package com.order.app.orderstatusservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderStatusEntity {

    @Id
    private String orderId;

    private String product;
    private int quantity;
    private String status;
}
