package com.alammar.orderservice.routes.order.commands;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.SagaPropagation;
import org.springframework.stereotype.Component;

@Component
public class CancelOrder extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("kafka:cancelOrder?brokers=localhost:9092")
                .saga()
                .propagation(SagaPropagation.MANDATORY)
                .to("saga:compensate")
                .to("kafka:orderCanceled?brokers=localhost:9092")
                .log("Order ${body} Canceled");
    }
}
