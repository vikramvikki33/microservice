package com.vr.microservice.faker.kafka.runner.impl;

import com.github.javafaker.Faker;
import com.vr.microservice.faker.kafka.config.FakerConfigProperties;
import com.vr.microservice.faker.kafka.domain.FakerModel;
import com.vr.microservice.faker.kafka.exceptions.FakerToKafkaServiceExceptions;
import com.vr.microservice.faker.kafka.listener.KafkaStatusListener;
import com.vr.microservice.faker.kafka.runner.StreamRunner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

@Component
@Slf4j
@ConditionalOnProperty(name = "faker-to-kafka-service.enable-mockdata", havingValue = "true")
public class FakerMockStream implements StreamRunner {

    private final FakerConfigProperties configProperties;
    private final KafkaStatusListener kafkaStatusListener;
    private final Faker faker;

    private static final Random random = new Random();

    public FakerMockStream(FakerConfigProperties configProperties, KafkaStatusListener kafkaStatusListener, Faker faker) {
        this.configProperties = configProperties;
        this.kafkaStatusListener = kafkaStatusListener;
        this.faker = faker;
    }

    @Override
    public void run() throws RuntimeException {
        String[] keywords = this.configProperties.getFakerKeywords().toArray(new String[0]);
        int maxLength = this.configProperties.getMaxMocklength();
        int minLength = this.configProperties.getMinMocklength();
        long sleepTime = this.configProperties.getMockSleepms();

        publishStream(keywords, minLength, maxLength, sleepTime);
    }

    private void publishStream(String[] keywords, int minLength, int maxLength, long sleepTime) {
        Executors.newSingleThreadExecutor().submit(() -> {
            try {
                while (true) {
                    int keyword = random.nextInt(0, keywords.length - 1);
                    String loremMessage = this.faker.lorem().sentence(maxLength);
                    FakerModel fakerModel = getRandomFakerObject(loremMessage, keywords[keyword]);
                    kafkaStatusListener.onStatus(fakerModel);
                    sleep(sleepTime);
                }
            } catch (FakerToKafkaServiceExceptions e) {
                log.error("Error in publishing stream", e);
            }
        });

    }

    private FakerModel getRandomFakerObject(String loremMessage, String keyword) {
        FakerModel f = new FakerModel();
        f.setId(ThreadLocalRandom.current().nextLong(Long.MAX_VALUE));
        f.setCreatedAt(LocalDateTime.now());
        f.setAuthor(keyword);
        f.setQuote(loremMessage);
        return f;
    }

    private void sleep(long sleepTime) {
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            throw new FakerToKafkaServiceExceptions("Error while sleeping");
        }
    }
}
