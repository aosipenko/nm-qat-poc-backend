package com.refinitiv.rest.controller;

import com.google.gson.Gson;
import com.refinitiv.rest.service.DBService;
import com.refinitiv.rest.service.KafkaService;
import com.refinitiv.rest.service.entity.Client;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/kafka")
public class KafkaController extends QATAbstractController {

    private Gson gson = new Gson();

    @Autowired
    public KafkaService kafkaService;

    @Autowired
    public DBService dbService;

    @GET
    @Path("/test")
    @Produces(MediaType.TEXT_PLAIN)
    public String getMirror(
        @QueryParam("mirror")
            String mirror) {
        return kafkaService.testRequest() + mirror;
    }

    @POST
    @Path("/post")
    @Produces(MediaType.TEXT_PLAIN)
    public void putValue(
        @QueryParam("value")
            String mirror) {
        kafkaService.storeData(mirror);
    }

    @GET
    @Path("/retrieve")
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveValues() {
        return constructOKResponse(kafkaService.retrieveData());
    }

    @GET
    @Path("/client")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(
        @QueryParam("id")
            long id) {
        return constructOKResponse(dbService.getClientById(id));
    }

    @POST
    @Path("/client/create")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createUser(String client) {
        return constructOKResponse(dbService.createClient(gson.fromJson(client, Client.class)));
    }

    @GET
    @Path("/test/{pathParam}")
    @Produces(MediaType.TEXT_PLAIN)
    public String getPathParam(
        @PathParam("pathParam")
            String mirror) {
        return mirror;
    }
}