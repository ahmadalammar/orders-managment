package com.alammar.customerservice.routes.credits.events;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class CreditsReserved extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("kafka:creditsReserved?brokers=localhost:9092")
                .log("Credit Reserved Event!");
    }
}
