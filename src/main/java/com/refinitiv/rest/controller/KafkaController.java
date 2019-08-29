package com.refinitiv.rest.controller;

import com.refinitiv.rest.service.KafkaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/kafka")
public class KafkaController extends SpringBeanAutowiringSupport {

    @Autowired
    public KafkaService kafkaService;

    @GET
    @Path("/test")
    @Produces(MediaType.TEXT_PLAIN)
    public String getMirror(@QueryParam("mirror") String mirror) {
        return kafkaService.testRequest() + mirror;
    }

    @GET
    @Path("/test/{pathParam}")
    @Produces(MediaType.TEXT_PLAIN)
    public String getPathParam(@PathParam("pathParam") String mirror) {
        return mirror;
    }
}
