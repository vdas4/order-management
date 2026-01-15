package com.order.app.orderingestionservice.route;

import com.order.app.orderingestionservice.model.Order;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.spi.IdempotentRepository;
import org.springframework.stereotype.Component;

@Component
public class FileToKafkaRoute extends RouteBuilder {

    private final IdempotentRepository idempotentRepository;

    public FileToKafkaRoute(IdempotentRepository idempotentRepository) {
        this.idempotentRepository = idempotentRepository;
    }

    @Override
    public void configure() throws Exception {
        from("file:C:/microservice-poc/order-management/order-ingestion-service/orders-inbox?noop=true")
                .routeId("file-to-kafka")
                .log("Camel is watching the orders-inbox directory for new files.")
                .log("File received: ${header.CamelFileName}")
                .idempotentConsumer(header("CamelFileName"), idempotentRepository)
                .log("Processing file: ${header.CamelFileName}")
                .convertBodyTo(String.class)
//                .unmarshal().json(Order.class)
//                .marshal().json(JsonLibrary.Jackson)
                .to("kafka:orders.new")
                .log("Order sent to Kafka topic 'orders.new': ${body}");
    }
}
