<%-- 
    Document   : Adminlogin
    Created on : 18-04-2017, 10:52:53
    Author     : Samil
--%>

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
        
        <%! src.GalgelogikService service = new src.GalgelogikService(); %>
        <%! src.GalgeI spil = service.getGalgelogikPort();%>
        <script>
            window.onload = function () {
                document.getElementById("letter").focus();
            };
        </script>
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
                                    <li><a href="highscore.jsp">Highscore</a></li>
                                    <li><a href="logout.jsp">Log ud</a></li>
                                </ul>
                            </nav>
                        </div>
                    </div>
                    </br>
                    <div class="inner cover">
                        <table style="width:100%" class="lead">
                            <tr class="a">
                                <th>Studienummer</th>
                            </tr>
                            <%
                                functions f = new functions();
                                ArrayList<String> users = f.getAllUsers();
                                for(int i=0; i<users.size();i++){
                                    out.println("<tr>");
                                    out.print("<td>"+users.get(i).toString()+"</td>");
                                    out.println("</tr>");
                                }
                            %>
                        </table>
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