package com.refinitiv.rest.controller;

import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.ws.rs.core.Response;

public abstract class QATAbstractController extends SpringBeanAutowiringSupport {

    protected Response constructOKResponse(Object content) {
        return Response.ok(content).header("Access-Control-Allow-Origin", "*").build();
    }

    protected Response constructClientErrorResponse() {
        return Response.status(Response.Status.BAD_REQUEST).header("Access-Control-Allow-Origin", "*").build();
    }

    protected Response constructClientNotFoundResponse() {
        return Response.status(Response.Status.NOT_FOUND).header("Access-Control-Allow-Origin", "*").build();
    }
}
