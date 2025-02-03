package com.vr.microservice.faker.kafka.service;

import com.vr.microservice.faker.kafka.runner.StreamRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FakerService {

    private final StreamRunner streamRunner;

    public FakerService(StreamRunner streamRunner) {
        this.streamRunner = streamRunner;
    }

    public void streamData() {
        this.streamRunner.run();
    }
}
