package com.order.app.orderstatusservice.routes;


import com.order.app.orderstatusservice.service.OrderStatusService;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class StatusRoutes extends RouteBuilder {

    @Value("${app.topics.new}")
    String newTopic;
    @Value("${app.topics.validated}")
    String validatedTopic;
    @Value("${app.topics.notified}")
    String notifiedTopic;
    @Value("${app.topics.dlt}")
    String dltTopic;

    private final OrderStatusService statusService;

    public StatusRoutes(OrderStatusService statusService) {
        this.statusService = statusService;
    }

    @Override
    public void configure() {

        onException(Exception.class)
                .handled(true)
                .useOriginalMessage()
                .log(LoggingLevel.ERROR, "Status update failed: ${exception.message}")
                .setHeader("errorMessage", simple("${exception.message}"))
                .toD("kafka:" + dltTopic);

        // You can consolidate to a single route using multiple topics:
        fromF("kafka:%s,kafka:%s,kafka:%s", newTopic, validatedTopic, notifiedTopic)
                .routeId("consume-order-events")
                .log(LoggingLevel.INFO, "Event on ${header[kafka.TOPIC]} key=${header[kafka.KEY]}")
                .process(this::apply)
                .log(LoggingLevel.INFO, "Order status updated.");
    }

    private void apply(Exchange ex) throws Exception {
        String json = ex.getMessage().getBody(String.class);
        statusService.applyEvent(json);
        // With allowManualCommit=true you could commit offset here using KafkaManualCommit if needed
    }
}
