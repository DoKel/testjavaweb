package com.webim;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class ShowFriendsServlet extends HttpServlet {

    public static long appid = 6713188;
    public static String secret = "c5KWmftKcXcEndndr45F";
    public static String order = "random";
    public static int count = 5;
    public static String apiVersion = "5.85";
    public static String fields = "first_name,last_name,photo_max";
    public static String redirectUrl = "http://188.166.194.200:8888/showFriends";
    

    private static String sendRequest(String urlstr) throws IOException{
        final StringBuilder result = new StringBuilder();
        URL url = new URL(urlstr);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            int codeResponse = urlConnection.getResponseCode();
            if(codeResponse != 200) throw new BadResponseException(codeResponse);
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");  // Put entire content to next token string, Converts utf8 to 16, Handles buffering for different width packets

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

    public void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws IOException {

        try {
            httpServletRequest.getSession().setAttribute("access_token", null);
            httpServletResponse.setContentType("text/html; charset=UTF-8");
            //if(httpServletRequest.getSession().getAttribute("access_token") == null) {
            if (this.getServletConfig().getServletContext().getAttribute("access_token") == null) {
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
                //httpServletRequest.getSession().setAttribute("access_token", token);
                this.getServletConfig().getServletContext().setAttribute("access_token", token);
            }


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
            address.append(this.getServletConfig().getServletContext().getAttribute("access_token"));
            address.append("&");

            address.append("v=");
            address.append(apiVersion);
            address.append("&");

            Date timeOfLastrequest = (Date) (this.getServletConfig().getServletContext().getAttribute("timeOfLastRequest"));
            if (timeOfLastrequest.getTime() - new Date().getTime() < 500) {
                this.getServletConfig().getServletContext().setAttribute("timeOfLastRequest", new Date());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            try {
                JSONObject vkResponse = new JSONObject(sendRequest(address.toString()));
                JSONArray items = vkResponse.getJSONObject("response").getJSONArray("items");

                ArrayList<UserInfo> friends = new ArrayList();

                for (int i = 0; i < items.length(); i++) {
                    JSONObject friend = items.getJSONObject(i);
                    friends.add(
                            new UserInfo(
                                    friend.getString("first_name"),
                                    friend.getString("last_name"),
                                    new URL(friend.getString("photo_max")),
                                    friend.getInt("id")
                            )
                    );
                }

                httpServletRequest.setAttribute("usersInfo", friends);
                try {
                    httpServletRequest.getRequestDispatcher("listOfFriends.jsp").forward(httpServletRequest, httpServletResponse);
                } catch (ServletException e) {
                    e.printStackTrace();
                }
            } catch (BadResponseException e) {
                httpServletResponse.sendRedirect("http://188.166.194.200:8888/redirectToAuth");
            }
        }catch(JSONException e){
            System.out.println(e);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            httpServletResponse.sendRedirect("http://188.166.194.200:8888/redirectToAuth");

        }
    }
}