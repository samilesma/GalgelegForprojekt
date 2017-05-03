<%-- 
    Document   : Adminlogin
    Created on : 18-04-2017, 10:52:53
    Author     : Samil
--%>

<%@page import="utils.connector"%>
<%@page import="utils.functions"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.util.ArrayList"%>
<%@page import="utils.functions"%>
<%-- 
    Document   : testjsp
    Created on : 13-03-2017, 20:03:13
    Author     : Umais
--%>
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
        <title>Galgeleg</title>
        <!-- <p class="navbar-text navbar-right">Signed in as <a href="#" class="navbar-link">Mark Otto</a></p> -->
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
        
        <%! galgeleg.GalgelogikService service = new galgeleg.GalgelogikService(); %>
        <%! galgeleg.GalgeI spil = service.getGalgelogikPort();%>
                <%
                String currUser = (String) request.getSession().getAttribute("currUser");
                boolean currAdmin = false;
                if(currUser != null){
                currAdmin = (boolean) request.getSession().getAttribute("currAdmin");
                System.out.println(currAdmin);
                }
                if(currAdmin) { %>
        <div class="site-wrapper">

            <div class="site-wrapper-inner">

                <div class="cover-container">

                    <div class="masthead clearfix">
                        <div class="inner">
                            <h3 class="masthead-brand">Galgeleg</h3>
                            <nav>
                                <ul class="nav masthead-nav">
                                    <li class="active"><a href="#">Admin</a></li>
                                    <li><a href="game.jsp">Spil</a></li>
                                    <li><a href="chat.jsp">Chat</a></li>
                                    <li><a href="challenges.jsp">Chall</a></li>
                                    <li><a href="highscore.jsp">Highscore</a></li>
                                    <li><a href="logout.jsp">Log ud</a></li>
                                </ul>
                            </nav>
                        </div>
                    </div>
                    </br>
                    <div class="inner cover">
                        
                        </br>
                        </br>
                        <h1 class="cover-heading">Highscore</h1>
                        <hr/>
                        <table style="width:100%" class="lead">
                            <tr class="a">
                                <th>Studienummer</th>
                                <th >Navn</th>
                                <th>Efternavn</th>
                                <th>Admin status</th>
                                <th>udfør </th>
                                <th>handlinger</th>
                            </tr>
                            <%
                                connector con=new connector();


                                ResultSet rUser =  con.select("SELECT sid,name,surname,admin FROM users");
                                while (rUser.next()) {
                                    out.println("<tr>");
                                    out.println("<form action='AdminServlet' method='post' style='margin:5px 0px;'>");
                                    out.print("<td>"+rUser.getString("sid")+"</td>");
                                    out.print("<td style='text-align:left;'> "+rUser.getString("name")+"</td>");
                                    out.print("<td style='text-align:left;'>"+rUser.getString("surname")+"</td>");
                                    out.print("<td>"+rUser.getInt("admin")+"</td>");

                                    
                                    %>
                                   
                                    <td style="margin-top:30px">
                                        
                                            <%
                                                
                                                out.print("<select name='ban' class='form-control'>");
                                                if(rUser.getInt("admin")==0){
                                                out.print("<option value='1day" +rUser.getString("sid")+ "'>Ban 1 day</option>");
                                                out.print("<option value='1week" +rUser.getString("sid")+ "'>Ban 1 week</option>"); 
                                                out.print("<option value='per" +rUser.getString("sid")+ "'>Ban perminently</option>");
                                                out.print("<option value='makadmin" +rUser.getString("sid")+ "'>Gør til admin</option>");
                                                } else {
                                                 out.print("<option value='remadmin" +rUser.getString("sid")+ "'>Fjern Admin</option>");
                                                }
                                                out.print("</select>");
                                                
                                                   

                                            %>
                                            
                                    </td>
                                    <input type="hidden" name="sid" value="<% rUser.getString("sid"); %>" />
                                    <td><input type='submit' /></td>
                                   

                                    
                                    <%
                                    out.println("</form>");
                                    out.println("</tr>");
                                }
                                    //String colour = request.getParameter("ban").toString();
                                   // out.println(colour);
                            %>
                        </table>
                        <hr/>
                        <br/>
                        <br/>
                    </div>
                    <div class="mastfoot">
                        <div class="inner">
                            <p>Lavet af gruppe <a href="http://tourneo.dk/">TS</a></p>
                        </div
                    </div>

                </div>

            </div>

        </div>
        <!-- <form method="post" action="start.jsp"><button type="submit" class="btn btn-primary">Login</button></form> -->
        <!-- Bootstrap core JavaScript
            ================================================== -->
        <!-- Placed at the end of the document so the pages load faster -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
        <script>window.jQuery || document.write('<script src="../../assets/js/vendor/jquery.min.js"><\/script>')</script>
        <script src="http://getbootstrap.com/dist/js/bootstrap.min.js"></script>
        <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
        <script src="http://getbootstrap.com/assets/js/ie10-viewport-bug-workaround.js"></script>
        <%} else if(currUser != null) {%>
        <jsp:forward page = "game.jsp" />
        <%} else {%>
        <jsp:forward page = "index.jsp" />
        <% }%>
        
    </body> 
</html>