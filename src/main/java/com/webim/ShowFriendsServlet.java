package com.webim;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class ShowFriendsServlet extends HttpServlet {

    public static long appid = 6713188;
    public static String secret = "c5KWmftKcXcEndndr45F";
    public static String order = "random";
    public static int count = 5;
    public static String apiVersion = "5.85";
    public static String fields = "first_name,last_name,photo_max";
    public static String redirectUrl = "http://127.0.0.1:8888/showFriends";

    private static String sendRequest(String urlstr) throws IOException{
        final StringBuilder result = new StringBuilder();
        URL url = new URL(urlstr);

        try (InputStream is = url.openStream()) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            reader.lines().forEach(result::append);
        }

        return result.toString();
    }

    public void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws IOException {
        try {
            httpServletResponse.setContentType("text/html; charset=UTF-8");

            String code = httpServletRequest.getParameter("code");

            StringBuilder adress = new StringBuilder();
            adress.append("https://oauth.vk.com/access_token?");

            adress.append("client_id=");
            adress.append(appid);
            adress.append("&");

            adress.append("client_secret=");
            adress.append(secret);
            adress.append("&");

            adress.append("redirect_uri=");
            adress.append(redirectUrl);
            adress.append("&");

            adress.append("code=");
            adress.append(code);
            adress.append("&");

            JSONObject tokenInfo = new JSONObject(sendRequest(adress.toString()));
            String token = tokenInfo.getString("access_token");


            StringBuilder address = new StringBuilder();

            address.append("https://api.vk.com/method/friends.get?");

            address.append("order=");
            address.append(order);
            address.append("&");

            address.append("count=");
            address.append(count);
            address.append("&");

            address.append("fields=");
            address.append(fields);
            address.append("&");

            address.append("access_token=");
            address.append(token);
            address.append("&");

            address.append("v=");
            address.append(apiVersion);
            address.append("&");

            JSONObject vkResponse = new JSONObject(sendRequest(address.toString()));
            JSONArray items = vkResponse.getJSONObject("response").getJSONArray("items");

            ArrayList<UserInfo> friends = new ArrayList();

            for (int i = 0; i < items.length(); i++) {
                JSONObject friend = items.getJSONObject(i);
                friends.add(
                        new UserInfo(
                                friend.getString("first_name"),
                                friend.getString("last_name"),
                                new URL(friend.getString("photo_max"))
                        )
                );
            }

            httpServletRequest.setAttribute("usersInfo", friends);
            try {
                httpServletRequest.getRequestDispatcher("listOfFriends.jsp").forward(httpServletRequest, httpServletResponse);
            } catch (ServletException e) {
                e.printStackTrace();
            }
        }catch(Exception e){
            System.out.println("Oooops... :c");
        }

    }
}