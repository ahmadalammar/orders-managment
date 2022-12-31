package com.alammar.customerservice.routes.credits.commands;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.SagaPropagation;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ReserveCredits extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        onException()
                .log("UnExpected Exception on Reserve Credits : ${body}")
                .log("Proceed to compensate")
                .to("kafka:cancelOrder?brokers=localhost:9092");

        from("kafka:reserveCredits?brokers=localhost:9092")
                .saga()
                .propagation(SagaPropagation.MANDATORY)
                .compensation("direct:refundCredits")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        log.info("Processing reserve Credits....");
                        //throw new IllegalStateException("Error reserving credits!");
                    }
                })
                .log("Credits ${body} reserved")
                .to("kafka:creditsReserved?brokers=localhost:9092")
                .to("kafka:completeOrder?brokers=localhost:9092");

        from("direct:refundCredits")
                .to("kafka:creditsRefunded?brokers=localhost:9092")
                .log("Credits ${body} refunded successfully!");
    }
}
