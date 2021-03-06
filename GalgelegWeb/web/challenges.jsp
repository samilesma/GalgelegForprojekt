<%@page import="java.text.SimpleDateFormat"%>
<%@page import="utils.connector"%>
<%@page import="utils.functions"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.util.ArrayList"%>
<%@page import="utils.functions"%>
<%@page import="java.text.Format"%>
<%@page import="java.sql.Date"%>
<%@page import="java.sql.Timestamp"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width = device-width, initial-scale = 1">
        <!-- Bootstrap core CSS -->
        <link href="http://getbootstrap.com/dist/css/bootstrap.min.css" rel="stylesheet">
        <!-- Custom styles for this template -->
        <link href="cover.css" rel="stylesheet">
        <link rel="icon" type="image/x-icon" href="/favicon.ico"/>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
        <script src="//maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
        <title>Galgeleg</title>
        <style>
            table {
                counter-reset: rowNumber;
            }

            table tr.notfirst {
                counter-increment: rowNumber;
            }

            table tr.notfirst td:first-child::before {
                content: counter(rowNumber);
                min-width: 1em;
                margin-right: 0.5em;
            }
            th {
                text-align: center;
            }
        </style>
    </head>
    <body>
        <%
            String currUser = (String) request.getSession().getAttribute("currUser");
        %>
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
                                    <li><a href="chat.jsp">Chat</a></li>
                                    <li class="active"><a href="#">Udfordringer</a></li>
                                    <li><a href="highscore.jsp">Highscore</a></li>
                                    <li><a href="logout.jsp">Log ud</a></li>
                                </ul>
                            </nav>
                        </div>
                    </div>
                    </br>
                    <div class="inner cover ts-content">
                        <h1 class="cover-heading">Challenges</h1>
                        <hr/>
                        <div id="tab" class="container">	
                            <ul  class="nav nav-pills">
                                <li class="active">
                                    <a  href="#1b" data-toggle="tab">Send udfordring</a>
                                </li>
                                <li><a href="#2b" data-toggle="tab">Sendte udfordringer</a>
                                </li>
                                <li><a href="#3b" data-toggle="tab">Mine udfordringer</a>
                                </li>
                            </ul>

                            <div class="tab-content clearfix">
                                <div class="tab-pane active" id="1b">
                                    <table style="width:850px" class="lead">
                                        <tr class="a">
                                            <th style='float:left;'>Navn</th>
                                            <th></th>
                                        </tr>
                                        <%
                                            connector con = new connector();
                                            ResultSet rUser = con.select("SELECT sid,name,surname FROM users ORDER BY name");
                                            while (rUser.next()) {
                                                if(!con.check("SELECT p1,p2 FROM challenges WHERE p1='"+currUser+"' "
                                                        + "AND p2='" + rUser.getString("sid")+ "' AND acceptchl=0")){
                                                    out.println("<tr>");
                                                    out.println("<form action='ChallengeServlet' method='post' style='margin:5px 0px;'>");
                                                    out.print("<td style='float:left;'>"+rUser.getString("name")+" "+rUser.getString("surname")+"</td>");
                                                    %>
                                                    <input type="hidden" name="sid" value="<%  out.print(rUser.getString("sid")); %>" />
                                                    <td style='width:350px;margin-left:-150px;'>
                                                        <input type='hidden' name='type' value='send' />
                                                        <input class="btn btn-success" type='submit' value='Send'>
                                                    </td>
                                                    <%
                                                    out.println("</form>");
                                                    out.println("</tr>");
                                                }
                                            }
                                        %>

                                    </table>
                                </div>
                                <div class="tab-pane" id="2b">
                                    <table style="width:780px" class="lead">
                                        <tr class="a">
                                            <th>Studienummer</th>
                                            <th>Navn</th>
                                            <th>Tidspunkt</th>
                                            <th>Status</th>
                                            <th></th>
                                        </tr>
                                        <%
                                            ResultSet cUser = con.select("SELECT challenges.id,p2,timestamp,acceptchl,users.name FROM challenges INNER JOIN users ON p2 = users.sid WHERE p1 ='"+currUser+"'");
                                            
                                            while (cUser.next()) {
                                                out.println("<tr>");
                                                out.println("<form action='ChallengeServlet' method='post' style='margin:5px 0px;'>");
                                                out.print("<td>" + cUser.getString("p2") + "</td>");
                                                out.print("<td>" + cUser.getString("users.name") + "</td>");
                                                Timestamp stamp = new Timestamp(cUser.getInt("timestamp")*1000L);
                                                Date date = new Date(stamp.getTime());
                                                Format format = new SimpleDateFormat("dd/MM-yyyy HH:mm");
                                                out.print("<td>"+format.format(date)+"</td>");
                                                
                                                if(cUser.getString("acceptchl").equals("0")){
                                                    out.print("<td>"+"Afventer"+"<td>");
                                                }
                                                else if(cUser.getString("acceptchl").equals("1")){
                                                    out.print("<td>"+"Accepteret"+"<td>");
                                        %>
                                                <input type="hidden" name="id" value="<%  out.print(cUser.getString("challenges.id")); %>" />
                                                <td style='width:50px;margin-left:15px;'>
                                                        <input class="btn btn-success" type='submit' value="Spil" >
                                                        <input type='hidden' name='type' value='spil' />
                                                </td>
                                        
                                        <%
                                                }
                                                else{
                                                    out.print("<td>"+"Afvist"+"<td>");
                                                }
                                                                                                
                                                out.println("</form>");
                                                out.println("</tr>");
                                            }
                                        %>

                                    </table>
                                </div>
                                <div class="tab-pane" id="3b">
                                    
                                     <table style="width:850px" class="lead">
                                        <tr class="a">
                                            <th>Navn</th>
                                            <th>Dato</th>
                                            <th></th>
                                        </tr>
                                        <%
                                            ResultSet myChallenges = con.select("SELECT challenges.id,p1,users.name,users.sid,timestamp FROM challenges INNER JOIN users ON p1=users.sid WHERE p2='"+currUser+"' AND acceptchl=0");
                                            
                                            while (myChallenges.next()) {
                                                    out.println("<tr>");
                                                    out.println("<form action='ChallengeServlet' method='post' style='margin:5px 0px;'>");
                                                    out.print("<td>" + myChallenges.getString("users.name") + "</td>");
                                                    Timestamp stamp = new Timestamp(myChallenges.getInt("timestamp")*1000L);
                                                    Date date = new Date(stamp.getTime());
                                                    Format format = new SimpleDateFormat("dd/MM-yyyy HH:mm");
                                                    out.print("<td>"+format.format(date)+"</td>");
                                                     %>
                                                    <input type="hidden" name="id" value="<%  out.print(myChallenges.getString("challenges.id")); %>" />
                                                    <td style='width:50px;margin-left:15px;'>
                                                        <input type='hidden' name='type' value='acceptdecline' />
                                                        <input class="btn btn-success" type='submit' name="sub" value="Accepter" >
                                                        <input class="btn btn-success" type='submit' name="sub" value="Afvis" >
                                                    </td>
                                                    
                                                    <%
                                                    out.println("</form>");
                                                    out.println("</tr>");
                                                }
                                            
                                        %>

                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="mastfoots">
                    <div class="inners">
                        <p>Lavet af gruppe <a href="http://tourneo.dk/">TS</a></p>
                    </div
                </div>

            </div>

        </div>

    </div>
</body>
</html>