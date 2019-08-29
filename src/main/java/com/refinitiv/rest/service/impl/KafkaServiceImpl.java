package com.refinitiv.rest.service.impl;

import com.refinitiv.rest.service.KafkaService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class KafkaServiceImpl implements KafkaService {

    private final ArrayList<String> dataStorage = new ArrayList<>();

    public String testRequest() {
        return "test value";
    }

    @Override
    public void storeData(String data) {
        dataStorage.add(data);
    }

    @Override
    public List<String> retrieveData() {
        return dataStorage;
    }

}
