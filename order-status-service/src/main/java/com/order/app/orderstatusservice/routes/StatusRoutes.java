package com.order.app.orderstatusservice.routes;

import com.order.app.orderstatusservice.model.Order;
import com.order.app.orderstatusservice.service.OrderStatusService;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class StatusRoutes extends RouteBuilder {

    private final OrderStatusService service;

    public StatusRoutes(OrderStatusService service) {
        this.service = service;
    }

    @Override
    public void configure() {

        from("kafka:orders.validated?consumersCount=1")
                .unmarshal().json(JsonLibrary.Jackson, Order.class)
                .bean(service, "onValidated");

        from("kafka:orders.rejected?consumersCount=1")
                .unmarshal().json(JsonLibrary.Jackson, Order.class)
                .bean(service, "onRejected");

        from("kafka:orders.notified?consumersCount=1")
                .unmarshal().json(JsonLibrary.Jackson, Order.class)
                .bean(service, "onNotificationSent");
    }
}
