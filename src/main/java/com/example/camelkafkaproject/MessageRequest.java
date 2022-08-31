package com.example.camelkafkaproject;

public record MessageRequest(String key,String message) {
//    @Override
//    public String toString() {
//        return "{\n" +
//                "\"key\":\"" + key + "\",\n" +
//                "\"message\":\"" + message + "\"\n" +
//                '}';
//    }

    public MessageRequest {
    }
}

