<%-- 
    Document   : index
    Created on : 13-03-2017, 06:54:00
    Author     : ahmad
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1><% out.println("Hello World! Din IP er: "+request.getRemoteAddr()); %></h1>
    </body>
</html>
