package com.refinitiv.rest.controller;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This class is responsible for adding Access-Control-Allow-Origin header. But it doesn't work properly for some reason.
 * A temporary solution - is to use QATAbstractController class as parent, and its methods to add this header where its needed
 */

@WebFilter(asyncSupported = true, urlPatterns = { "/*" })
public class CORSFilter implements Filter {

    public void destroy() { }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        // Authorize (allow) all domains to consume the content
        ((HttpServletResponse) servletResponse).addHeader("Access-Control-Allow-Origin", "*");
        ((HttpServletResponse) servletResponse).addHeader("Access-Control-Allow-Methods","GET, OPTIONS, HEAD, PUT, POST");

        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        // For HTTP OPTIONS verb/method reply with ACCEPTED status code -- per CORS handshake
        if (request.getMethod().equals("OPTIONS")) {
            resp.setStatus(HttpServletResponse.SC_ACCEPTED);
            return;
        }

        // pass the request along the filter chain
        chain.doFilter(request, servletResponse);
    }

    public void init(FilterConfig fConfig) throws ServletException {
        // TODO Auto-generated method stub
    }

}