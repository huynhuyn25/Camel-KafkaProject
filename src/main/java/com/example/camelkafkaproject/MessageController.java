package com.example.camelkafkaproject;

import org.apache.camel.*;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.kafka.KafkaConstants;
import org.apache.camel.support.DefaultExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/messages")
public class MessageController {
    @Autowired
    private ProducerTemplate template;
    private MessageRequest messageRequest;
    private int check = 0;
    @PostMapping
    public ResponseEntity<String> publish(@RequestBody MessageRequest request){
        if(request==null) return ResponseEntity.ok("Null");
//        check = 1;
        messageRequest=request;
        template.sendBodyAndHeader("direct:hello",request.message(),KafkaConstants.KEY,request.key());
        return  ResponseEntity.ok("Message sent to the topic!");
    }

    @Component
    public class SimpleRouteBuilder extends RouteBuilder {
        @Override
        public void configure(){
            try{
                Predicate predicate = new Predicate() {
                    @Override
                    public boolean matches(Exchange exchange) {
                        if(messageRequest.message().equals("hehehe")) return true;
                        return false;
                    }
                };
                from("direct:hello").choice().when(predicate)
                        .to("kafka:HuynHuyn1?brokers=localhost:9092")
                        .otherwise()
                        .to("kafka:HuynHuyn2?brokers=localhost:9092");



            }catch (Exception e){
                e.printStackTrace();
            }

        };
    }



}