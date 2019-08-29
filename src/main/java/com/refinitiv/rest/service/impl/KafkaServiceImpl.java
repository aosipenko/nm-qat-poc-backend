package com.refinitiv.rest.service.impl;

import com.refinitiv.rest.service.KafkaService;
import org.springframework.stereotype.Component;

@Component
public class KafkaServiceImpl implements KafkaService {

    public String testRequest() {
        return "test value";
    }

}
