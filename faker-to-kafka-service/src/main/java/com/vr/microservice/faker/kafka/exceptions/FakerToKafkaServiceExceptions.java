package com.vr.microservice.faker.kafka.exceptions;

public class FakerToKafkaServiceExceptions extends RuntimeException {
    public FakerToKafkaServiceExceptions() {
        super();
    }

    public FakerToKafkaServiceExceptions(String message) {
        super(message);
    }

    public FakerToKafkaServiceExceptions(String message, Throwable cause) {
        super(message, cause);
    }
}
