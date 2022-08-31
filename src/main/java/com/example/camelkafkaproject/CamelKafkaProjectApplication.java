package com.example.camelkafkaproject;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class CamelKafkaProjectApplication {

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(CamelKafkaProjectApplication.class, args);
        CamelContext cmt = new DefaultCamelContext();
        try{
            cmt.addRoutes(new RouteBuilder() {
                @Override
                public void configure() throws Exception {
                    from("file:input?noop=true").split().tokenize("\n").to("file:output");
                }
            });
            while (true){
                cmt.start();
            }
        }catch (Exception e){
            e.printStackTrace();
        }


    }
}
