package com.alammar.orderservice.routes.order.commands;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.SagaPropagation;
import org.springframework.stereotype.Component;

@Component
public class CompleteOrder extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("kafka:completeOrder?brokers=localhost:9092")
                .saga()
                .propagation(SagaPropagation.MANDATORY)
                .to("saga:complete")
                .to("kafka:orderCompleted?brokers=localhost:9092")
                .log("Order ${body} Completed Successfully!");
    }
}
