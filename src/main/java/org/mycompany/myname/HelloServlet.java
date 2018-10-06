package org.mycompany.myname;

/**
 * Created by anon on 1/10/2017.
 */

import javax.servlet.http.*;
import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.HttpServlet;


public class HelloServlet extends HttpServlet {
    public void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws IOException {


        httpServletResponse.setContentType("text/html");
        RequestDispatcher dispatcher = httpServletRequest.getRequestDispatcher("auth.html");
        if (dispatcher != null) {
            try {
                dispatcher.forward(httpServletRequest, httpServletResponse);
            } catch (ServletException e) {
                e.printStackTrace();
            }
        }
    }
}