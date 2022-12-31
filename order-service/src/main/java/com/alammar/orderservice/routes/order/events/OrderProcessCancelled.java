package com.alammar.orderservice.routes.order.events;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class OrderProcessCancelled extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("kafka:orderProcessCancelled?brokers=localhost:9092")
                .log("Order Process Cancelled Event!");
    }
}
