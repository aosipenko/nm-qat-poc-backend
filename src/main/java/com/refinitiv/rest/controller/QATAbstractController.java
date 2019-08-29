package com.refinitiv.rest.controller;

import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.ws.rs.core.Response;

public abstract class QATAbstractController extends SpringBeanAutowiringSupport {

    protected Response constructOKResponse(Object content){
        return Response.ok(content)
                .header("Access-Control-Allow-Origin", "*")
                .build();
    }
}
