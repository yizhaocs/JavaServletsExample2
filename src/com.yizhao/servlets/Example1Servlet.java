package com.yizhao.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by yzhao on 10/5/16.
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
}
