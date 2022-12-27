package com.alammar.orderservice.routes;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.stereotype.Component;

import java.util.Timer;

@Component
public class CreateOrderRoute extends RouteBuilder {
    JacksonDataFormat jsonDataFormat = new JacksonDataFormat(Timer.class);

    @Override
    public void configure() throws Exception {
        rest("/say")
                .get("/hello").to("direct:hello")
                .get("/bye").consumes("application/json").to("direct:bye")
                .post("/bye").to("mock:update");

        from("direct:hello")
                .transform().constant("Hello World");

        from("direct:bye")
                .transform().constant("Bye World");
    }

    public class TimerProcessor implements Processor {
        public void process(Exchange exchange) throws Exception {
            String id = exchange.getIn().getBody(String.class);
            Timer t = new Timer(id);
            exchange.getIn().setBody(t);
        }
    }
}
