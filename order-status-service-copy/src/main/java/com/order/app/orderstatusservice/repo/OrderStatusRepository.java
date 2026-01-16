package com.order.app.orderstatusservice.repo;

import com.order.app.orderstatusservice.entity.OrderStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderStatusRepository extends JpaRepository<OrderStatusEntity, String> {}