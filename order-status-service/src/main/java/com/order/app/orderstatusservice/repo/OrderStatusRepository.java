package com.order.app.orderstatusservice.repo;

import com.order.app.orderstatusservice.entity.OrderStatusEntity;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface OrderStatusRepository extends JpaRepository<OrderStatusEntity, String> {

    @Modifying
    @Query("UPDATE OrderStatusEntity o SET o.product = :product, o.quantity = :quantity, o.status = :status WHERE o.orderId = :orderId")
    int updateStatus(@Param("orderId") String orderId, @Param("product") String product, @Param("quantity") int quantity, @Param("status") String status);
}