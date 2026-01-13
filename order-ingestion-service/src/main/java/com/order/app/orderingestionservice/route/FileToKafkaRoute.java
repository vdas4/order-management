package com.order.app.orderingestionservice.route;

import com.order.app.orderingestionservice.model.Order;
import org.apache.camel.builder.RouteBuilder;
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
        from("file:orders-inbox?noop=true")
                .routeId("file-to-kafka")
                .idempotentConsumer(header("CamelFileName"), idempotentRepository)
                .log("Processing file: ${header.CamelFileName}")
                .unmarshal().json(Order.class)
                .to("kafka:orders.new");
    }
}
