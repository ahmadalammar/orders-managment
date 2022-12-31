package com.alammar.customerservice.routes.credits.events;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class CreditsRefunded extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("kafka:creditsRefunded?brokers=localhost:9092")
                .log("Credits Refunded Event!");
    }
}
