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
<%
String currName = (String) request.getSession().getAttribute("currName");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width = device-width, initial-scale = 1">
        <link rel="stylesheet" href="http://getbootstrap.com/dist/css/bootstrap.css">
        <link rel="stylesheet" href="cover.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <script src="ajax.js"></script>
        <script>
        function escapeHtml(text) {
            return text.replace(/&/g, "&amp;").replace(/</g, "&lt;").replace(/>/g, "&gt;").replace(/"/g, "&quot;").replace(/'/g, "&#039;");
        }
        
        function setMsg(msg,sid,time)
        {
            $("div#chatbox").append("<div class='mesg'><div class='name'>"+sid+":<span style='margin-left:1em'> </span></div><div class='msg'>"+escapeHtml(msg)+"</div></div><br/>");
            $("#chatbox").scrollTop($("#chatbox").prop("scrollHeight"));
        
        }
        
        $("document").ready(function(){
            $("form").submit(function(){
                setMsg($(this).find("#usermsg").val(),"<%=currName%>",new Date() / 1000 | 0);
                $("#usermsg").val("");
            });
        });
        
        setInterval(function(){
            var date = (new Date() / 1000 | 0)-3;
            $.ajax({
                cache:false,
                type:"POST",
                dataType: "json",
                url:"ChatServlet",
                data:{type:"getmessage",date:date},
                success:function(data)
                {
                    for(var i=1; i<=data[0].length; i++) {
                        setMsg(data[1][i-1],data[0][i-1],data[2][i-1]);
                    }
                }
            });
        },3000);
        </script>
        <link rel="icon" type="image/x-icon" href="/favicon.ico"/>
        <title>Galgeleg</title>
    </head>
    <body>
    <div class="site-wrapper">
        <div class="site-wrapper-inner">
            <div class="cover-container">
                <div class="masthead clearfix">
                    <div class="inner">
                        <h3 class="masthead-brand">Galgeleg</h3>
                        <nav>
                            <ul class="nav masthead-nav">
                                <li><a href="admin.jsp">Admin</a></li>
                                <li><a href="game.jsp">Spil</a></li>
                                <li class="active"><a href="#">Chat</a></li>
                                <li><a href="challenges.jsp">Chall</a></li>
                                <li><a href="highscore.jsp">Highscore</a></li>
                                <li><a href="logout.jsp">Log ud</a></li>
                            </ul>
                        </nav>
                    </div>
                </div>
                <br/>
                <div class="inner cover">
                    <h1>Chat</h1>
                    <div id="menu">
                        <p class="welcome">Galgelegs Chat <b></b></p>
                        <div style="clear:both"></div>
                    </div>
                    <div id="chatbox" style="width:50%; height:500px; margin:auto; border:2px solid #929391; border-radius:20px;"></div>
                    <br/>
                    <form action="ChatServlet" method="post" class="ajax">
                        <input name="usermsg" type="text" id="usermsg" style="width:400px" />
                        <button type="submit" style="width:100px;">Send</button>
                    </form>
                </div>
                <div class="mastfoot">
                    <div class="inner">
                        <p>Lavet af gruppe <a href="http://tourneo.dk/">TS</a></p>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>