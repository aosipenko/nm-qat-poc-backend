package com.refinitiv.rest.controller;

import com.google.gson.Gson;
import com.refinitiv.rest.service.DBService;
import com.refinitiv.rest.service.KafkaService;
import com.refinitiv.rest.service.entity.Client;
import com.refinitiv.rest.service.exception.BadClientDataException;
import com.refinitiv.rest.service.exception.NoSuchClientException;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
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
    @Path("/items")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllItems() {
        return constructOKResponse(dbService.getAllItems());
    }

    @GET
    @Path("/item/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getItemById(
        @PathParam("id")
            Long id) {
        return constructOKResponse(dbService.getItemById(id));
    }

    @GET
    @Path("/client")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(
        @QueryParam("id")
            long id) {
        try {
            return constructOKResponse(dbService.getClientById(id));
        } catch (NoSuchClientException e) {
            return constructClientNotFoundResponse();
        }
    }

    @GET
    @Path("/client/all")
    public Response getClientsList() {
        return constructOKResponse(dbService.getAllClients());
    }

    @GET
    @Path("/client/{id}/contractors")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getContractorsForClient(
        @PathParam("id")
            Long id) {
        try {
            return constructOKResponse(dbService.getContractorsForClient(id));
        } catch (NoSuchClientException e) {
            return constructClientNotFoundResponse();
        }
    }

    @POST
    @Path("/client/create")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createUser(String client) throws Exception {
        try {
            return constructOKResponse(dbService.createClient(gson.fromJson(client, Client.class)));
        } catch (BadClientDataException e) {
            return constructClientErrorResponse();
        }
    }

    @POST
    @Path("/client/{id}/update")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateUser(
        @PathParam("id")
            Long id, String clientData) throws Exception {
        try {
            return constructOKResponse(dbService.updateClient(id, gson.fromJson(clientData, Client.class)));
        } catch (NoSuchClientException n) {
            return constructClientNotFoundResponse();
        } catch (BadClientDataException e) {
            return constructClientErrorResponse();
        }
    }

    @DELETE
    @Path("/client/{id}/delete")
    public Response deleteUser(
        @PathParam("id")
            Long id) {
        return constructOKResponse(dbService.deleteClient(id));
    }
}