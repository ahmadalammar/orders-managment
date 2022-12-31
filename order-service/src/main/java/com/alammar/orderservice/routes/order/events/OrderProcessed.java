package com.alammar.orderservice.routes.order.events;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class OrderProcessed extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("kafka:orderProcessed?brokers=localhost:9092")
                .log("Order Processed Event!");
    }
}
