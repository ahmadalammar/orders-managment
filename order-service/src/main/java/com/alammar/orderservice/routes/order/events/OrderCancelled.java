package com.alammar.orderservice.routes.order.events;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class OrderCancelled extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("kafka:orderCanceled?brokers=localhost:9092")
                .log("Order Cancelled Event!");
    }
}
