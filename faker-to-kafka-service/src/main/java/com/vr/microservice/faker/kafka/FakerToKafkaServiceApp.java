package com.vr.microservice.faker.kafka;

import com.vr.microservice.faker.kafka.config.FakerConfigProperties;
import com.vr.microservice.faker.kafka.service.FakerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
public class FakerToKafkaServiceApp implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(FakerToKafkaServiceApp.class);
    private final FakerService fakerService;

    public FakerToKafkaServiceApp(FakerService fakerService) {
        this.fakerService = fakerService;
    }

    public static void main(String[] args) {
        SpringApplication.run(FakerToKafkaServiceApp.class, args);
    }

    @Override
    public void run(String... args)  {
        logger.info("Starting FakerToKafkaServiceApp......");
        this.fakerService.streamData();
    }
}