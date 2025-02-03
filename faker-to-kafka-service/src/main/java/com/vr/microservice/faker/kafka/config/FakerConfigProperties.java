package com.vr.microservice.faker.kafka.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "faker-to-kafka-service")
@Setter
@Getter
public class FakerConfigProperties {

    private List<String> fakerKeywords;
    private Boolean enableMockdata;
    private long mockSleepms;
    private int minMocklength;
    private int maxMocklength;
}
