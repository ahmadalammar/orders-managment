package com.alammar.orderservice.routes;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.SagaCompletionMode;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CreateOrderRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        from("kafka:createOrder?brokers=localhost:9092")
                .log("Order Received ${body}")
                .saga()
                .completionMode(SagaCompletionMode.MANUAL)
                .compensation("direct:cancelOrder")
                .completion("direct:completeOrder")
                .to("kafka:processOrder?brokers=localhost:9092");

        from("direct:completeOrder")
                .log("Order ${body} Completed Successfully!");

        from("direct:cancelOrder")
                .log("Order ${body} Canceled");
    }
}
