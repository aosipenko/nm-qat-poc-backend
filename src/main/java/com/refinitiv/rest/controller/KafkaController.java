package com.refinitiv.rest.controller;

import com.refinitiv.rest.service.KafkaService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/kafka")
public class KafkaController extends QATAbstractController {

    @Autowired
    public KafkaService kafkaService;

    @GET
    @Path("/test")
    @Produces(MediaType.TEXT_PLAIN)
    public String getMirror(@QueryParam("mirror") String mirror) {
        return kafkaService.testRequest() + mirror;
    }

    @POST
    @Path("/post")
    @Produces(MediaType.TEXT_PLAIN)
    public void putValue(@QueryParam("value") String mirror) {
        kafkaService.storeData(mirror);
    }

    @GET
    @Path("/retrieve")
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveValues() {
        return constructOKResponse(kafkaService.retrieveData());
    }

    @GET
    @Path("/test/{pathParam}")
    @Produces(MediaType.TEXT_PLAIN)
    public String getPathParam(@PathParam("pathParam") String mirror) {
        return mirror;
    }
}