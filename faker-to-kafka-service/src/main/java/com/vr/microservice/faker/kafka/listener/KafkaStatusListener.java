package com.vr.microservice.faker.kafka.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vr.microservice.faker.kafka.domain.FakerModel;
import com.vr.microservice.faker.kafka.exceptions.FakerToKafkaServiceExceptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaStatusListener {

    @Autowired
    private ObjectMapper objectMapper;

    public void onStatus(FakerModel fakerModel) {
        log.info("Faker status: {}", logSerializedData(fakerModel));
    }

    private String logSerializedData(FakerModel fakerModel) {
        try {
           return  this.objectMapper.writeValueAsString(fakerModel);
        } catch (JsonProcessingException e) {
            throw new FakerToKafkaServiceExceptions("error in logSerializedData ", e);
        }
    }
}
