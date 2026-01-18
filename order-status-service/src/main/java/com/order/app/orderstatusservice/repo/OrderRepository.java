package com.order.app.orderstatusservice.repo;

import com.order.app.orderstatusservice.entity.OrderEntity;
import com.order.app.orderstatusservice.enums.NotificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderRepository extends JpaRepository<OrderEntity, String> {

    @Modifying
    @Query("""
        update OrderEntity o
        set o.notificationStatus = :status
        where o.orderId = :orderId
    """)
    int updateNotificationStatus(
            @Param("orderId") String orderId,
            @Param("status") NotificationStatus status
    );
}