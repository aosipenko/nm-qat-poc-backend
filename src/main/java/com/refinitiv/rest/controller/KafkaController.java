package com.refinitiv.rest.controller;

import org.apache.kafka.common.protocol.types.Field;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/kafka")
public class KafkaController {

    @GET
    @Path("/test")
    @Produces(MediaType.TEXT_PLAIN)
    public String getMirror(@QueryParam("mirror") String mirror) {
        return mirror;
    }

    @GET
    @Path("/test/{pathParam}")
    @Produces(MediaType.TEXT_PLAIN)
    public String getPathParam(@PathParam("pathParam") String mirror) {
        return mirror;
    }
}
