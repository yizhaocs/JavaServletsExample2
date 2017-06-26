package com.yizhao.servlets;

import org.springframework.web.HttpRequestHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

/**
 * curl "http://localhost:8080/example2?a=1&b=2"
 */
public class Example2Servlet implements HttpRequestHandler {
    public void init() throws ServletException {
    }

    public void destroy() throws ServletException {
    }

    public void handleRequest(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        System.out.println("lololol");
        for(String s: req.getParameterMap().keySet()){
            String[] value = req.getParameterMap().get(s);
            System.out.println(s + "=" + Arrays.toString(value) + "\n");
        }
    }
}
