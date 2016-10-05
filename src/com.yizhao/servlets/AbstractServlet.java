package com.yizhao.servlets;

import org.springframework.web.HttpRequestHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * curl http://localhost:8080/ps/ps
 */
public abstract class AbstractServlet implements HttpRequestHandler {
    //private static final Logger log = Logger.getLogger(AbstractServlet.class);

    public final void handleRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            handleRequestInner(request, response);
        }catch(Exception e){

        }
    }

    protected abstract void handleRequestInner(HttpServletRequest request,
                                               HttpServletResponse response) throws Exception;

    public void init() throws ServletException {
    }
}
