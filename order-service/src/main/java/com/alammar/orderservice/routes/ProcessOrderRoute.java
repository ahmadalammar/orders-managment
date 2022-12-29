package com.alammar.orderservice.routes;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.SagaPropagation;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ProcessOrderRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        onException()
                .log("UnExpected Exception Happened on Processing Order : ${body}")
                .log("Proceed to compensate")
                .to("saga:compensate");

        from("kafka:processOrder?brokers=localhost:9092")
                .saga()
                .propagation(SagaPropagation.MANDATORY)
                .compensation("direct:cancelProcessOrder")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        log.info("Processing orderId: " + exchange.getIn().getBody());
                    }
                })
                .log("Process Order ${body} Completed!")
                .to("kafka:reserveCredits?brokers=localhost:9092");

        from("direct:cancelProcessOrder")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        log.info("Cancelling Processing OrderId: " + exchange.getIn().getBody());
                        //throw new IllegalStateException("Error Canceling process order!!!!!");
                    }
                })
                .log("Process Order ${body} cancelled successfully!");
    }
}
