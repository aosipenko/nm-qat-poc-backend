package com.refinitiv.rest.service;

import java.util.List;

public interface KafkaService {

    String testRequest();

    void storeData(String data);
    List<String> retrieveData();

}
