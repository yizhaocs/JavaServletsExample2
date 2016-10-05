package com.yizhao.servlets;

import org.springframework.web.HttpRequestHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * curl http://localhost:8080/example1
 */
public abstract class AbstractServlet implements HttpRequestHandler {

    public void init() throws ServletException {
    }

    public void destroy() throws ServletException {
    }

    public final void handleRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            handleRequestInner(request, response);
        }catch(Exception e){

        }
    }

    protected abstract void handleRequestInner(HttpServletRequest request,
                                               HttpServletResponse response) throws Exception;


}
