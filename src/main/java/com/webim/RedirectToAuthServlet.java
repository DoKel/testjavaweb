package org.mycompany.myname;

/**
 * Created by anon on 1/10/2017.
 */

import javax.servlet.http.*;
import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.HttpServlet;


public class HelloServlet extends HttpServlet {

    public static long appid = 6713188;
    public static String displayType = "page";
    public static String redirectUrl = "127.0.0.1:8888/";
    public static String responseType = "code";
    public static String apiVersion = "5.85";

    public void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws IOException {
        StringBuilder address = new StringBuilder();

        address.append("https://oauth.vk.com/authorize?");

        address.append("client_id=");
        address.append(appid);
        address.append("&");

        address.append("display=");
        address.append(displayType);
        address.append("&");

        address.append("redirect_uri=");
        address.append(redirectUrl);
        address.append("&");

        address.append("response_type=");
        address.append(responseType);
        address.append("&");

        address.append("v=");
        address.append(apiVersion);
        address.append("&");

        httpServletResponse.sendRedirect(address.toString());
    }
}