package com.order.app.notificationservice.route;

import com.order.app.notificationservice.model.Order;
import com.order.app.notificationservice.service.NotificationService;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

@Component
public class NotificationRoute extends RouteBuilder {
    private final NotificationService notificationService;

    public NotificationRoute(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @Override
    public void configure() throws Exception {
        from("kafka:orders.validated")
                .routeId("notify")
                .log("Received new order from Kafka: ${body}")
                .convertBodyTo(String.class)
                .unmarshal().json(JsonLibrary.Jackson, Order.class)
                .log("Deserialized Order: ${body}")
                .bean(notificationService, "send")
                .log("Notification processed for Order ID: ${body}")
                .marshal().json(JsonLibrary.Jackson)
                .to("kafka:orders.notified")
                .log("Order notification sent to Kafka topic 'orders.notified': ${body}");
    }
}
