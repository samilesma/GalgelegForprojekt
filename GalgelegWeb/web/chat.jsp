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
        <script src="ajax.js"></script>
        <script>
        setInterval(function(){
            var date = (new Date() / 1000 | 0)-2;
            $.ajax({
                cache:false,
                type:"POST",
                dataType: "json",
                url:"ChatServlet",
                data:{type:"getmessage",date:date},
                success:function(data)
                {
                    alert(data);
                }
            });
        },3000);
        </script>
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
     
    <form action="ChatServlet" method="post" class="ajax">
        <div style="width:50%; height:500px; margin:auto; border:2px solid #929391; border-radius:20px;"></div>
        <br>
        <input name="usermsg" type="text" id="usermsg" style="width:400px" />
        <button type="submit" style="width:100px;">Send</button>
    </form>
</div>
        
    </body>
</html>
