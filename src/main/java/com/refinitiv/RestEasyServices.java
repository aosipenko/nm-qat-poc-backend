package com.refinitiv;

import com.refinitiv.rest.controller.KafkaController;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/api")
public class RestEasyServices extends Application {

    private Set<Object> singletons = new HashSet<Object>();

    public RestEasyServices() {
        singletons.add(new KafkaController());
    }

    @Override
    public Set<Object> getSingletons() {
        return singletons;
    }
}