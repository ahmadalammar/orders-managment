package com.alammar.customerservice.routes;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.SagaPropagation;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ReserveCreditsRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        onException()
                .log("UnExpected Exception on Reserve Credits : ${body}")
                .log("Proceed to compensate")
                .to("saga:compensate");

        onCompletion()
                .onCompleteOnly()
                .log("Reserved Credit Successfully!, Processed to complete the order!")
                .to("saga:complete");

        from("kafka:reserveCredits?brokers=localhost:9092")
                .saga()
                .propagation(SagaPropagation.MANDATORY)
                .compensation("direct:refundCredit")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        log.info("Processing reserve Credits....");
                        throw new IllegalStateException("Error reserving credits!");
                    }
                })
                .log("Credit ${body} reserved in action ${body}");

        from("direct:refundCredit")
                .log("Credits ${body} refunded successfully!");
    }
}
