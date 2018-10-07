package com.webim;

import javax.servlet.http.*;
import java.io.IOException;
import java.util.Date;
import javax.servlet.http.HttpServlet;


public class RedirectToAuthServlet extends HttpServlet {

    public static long appid = 6713188;
    public static String displayType = "page";
    public static String redirectUrl = "http://188.166.194.200/showFriends";
    public static String permissions = "friends,offline";
    public static String responseType = "code";
    public static String apiVersion = "5.85";

    public void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws IOException {
        this.getServletConfig().getServletContext().setAttribute("timeOfLastRequest" , new Date());
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

        address.append("scope=");
        address.append(permissions);
        address.append("&");

        address.append("response_type=");
        if(this.getServletConfig().getServletContext().getAttribute("access_token") == null) {
            address.append(responseType);
        }
        else address.append("token");
        address.append("&");

        address.append("v=");
        address.append(apiVersion);
        address.append("&");
        Date timeOfLastrequest = (Date)(this.getServletConfig().getServletContext().getAttribute("timeOfLastRequest"));
        if(timeOfLastrequest.getTime() - new Date().getTime() < 500) {
            this.getServletConfig().getServletContext().setAttribute("timeOfLastRequest", new Date());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        httpServletResponse.sendRedirect(address.toString());
    }
}