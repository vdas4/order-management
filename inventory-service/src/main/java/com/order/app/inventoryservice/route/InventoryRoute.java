package com.order.app.inventoryservice.route;

import com.order.app.inventoryservice.model.Order;
import com.order.app.inventoryservice.service.InventoryService;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

@Component
public class InventoryRoute extends RouteBuilder {
    private final InventoryService inventoryService;
    public InventoryRoute(InventoryService inventoryService){
        this.inventoryService = inventoryService;
    }
    @Override
    public void configure() throws Exception {
        errorHandler(deadLetterChannel("kafka:orders.rejected")
                .maximumRedeliveries(2)
                .redeliveryDelay(1000)
                .log("Order validation failed in Inventory Service: ${body.orderId}"));
        from("kafka:orders.new")
                .routeId("inventory-check")
                .log("Inventory received raw message: ${body}")
                .unmarshal().json(JsonLibrary.Jackson,Order.class)
                .log("Received order in Inventory Service: ${body.orderId}")
                .log("Checking inventory for order: ${body.orderId}")
                .bean("inventoryService", "validateOrder")
                .marshal().json(JsonLibrary.Jackson)
                .to("kafka:orders.validated")
                .log("Order validated in Inventory Service: ${body.OrderId}");

    }
}
