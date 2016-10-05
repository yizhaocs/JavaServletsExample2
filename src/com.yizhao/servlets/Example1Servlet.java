package com.yizhao.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * curl http://localhost:8080/example1
 */
public class Example1Servlet extends  AbstractServlet {
    @Override
    protected void handleRequestInner(HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {

    }

    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    public void destroy() throws ServletException {
        super.destroy();
    }
}
