<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.webim.UserInfo"%>

<head>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css">
</head>
<body>
<h3 class="center-align">Five Random Friends</h3>
<div class="row" style="display: flex; justify-content: center;">
<% List<UserInfo> usersInfo = (ArrayList<UserInfo>)request.getAttribute("usersInfo");

    for(UserInfo userInfo : usersInfo)
    {
        out.print("<a href=\"http:\/\/vk.com\/id"+userInfo.getId()+"\">");
        out.print("<div class=\"col s6 m2\" style=\"margin-left:0;\"><div class=\"card z-depth-4\"><div class=\"card-image\">");
        out.print("<img class=\"circle\" src=\""+userInfo.getPhotoUrl()+"\"></div>");
        out.print("<span class=\"card-title\">");
        out.print(userInfo.getFirstName()+" " + userInfo.getLastName());
        out.print("</span>");
        out.print("</div></div>");
        out.print("</a>");
    }

%>
</body>