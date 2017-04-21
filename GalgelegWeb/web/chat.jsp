<%-- 
    Document   : chat
    Created on : 20-04-2017, 18:08:35
    Author     : ibsenb
--%>

<%@page import="utils.connector" %>
<%@page import="utils.functions" %>
<%@page import="java.sql.ResultSet" %>
<%@page import="java.util.ArrayList" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width = device-width, initial-scale = 1">
        <link rel="stylesheet" href="http://getbootstrap.com/dist/css/bootstrap.css">
        <link rel="stylesheet" href="cover.css">
        <title>Galgeleg</title>
    </head>
    <body>
        <h1>Chat</h1>
        <div class="site-wrapper">
    <div id="menu">
        <p class="welcome">Galgelegs Chat <b></b></p>
        <div style="clear:both"></div>
    </div>
     
    <div id="chatbox"></div>
     
    <form name="message" action="">
        <input name="allmsg" type="text" id="allmsg" size="63" style="margin-right: 50px; height:200px"/> <br><br>
        <input name="usermsg" type="text" id="usermsg" size="63" />
        <input name="submitmsg" type="submit"  id="submitmsg" value="Send" />
    </form>
</div>
        
    </body>
</html>
