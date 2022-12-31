package com.alammar.orderservice.routes.order.events;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class OrderCompleted extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("kafka:orderCompleted?brokers=localhost:9092")
                .log("Order Completed Event!");
    }
}
