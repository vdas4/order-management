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

        from("kafka:orders.validated")
                .unmarshal().json(JsonLibrary.Jackson, Order.class)
                .bean(service, "save(${body}, 'VALIDATED')");

        from("kafka:orders.rejected")
                .unmarshal().json(JsonLibrary.Jackson, Order.class)
                .bean(service, "save(${body}, 'REJECTED')");

        from("kafka:orders.notified")
                .unmarshal().json(JsonLibrary.Jackson, Order.class)
                .bean(service, "save(${body}, 'NOTIFIED')");
    }
}
