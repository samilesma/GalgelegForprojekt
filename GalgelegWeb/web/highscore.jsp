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

        <div class="site-wrapper">

            <div class="site-wrapper-inner">

                <div class="cover-container">

                    <div class="masthead clearfix">
                        <div class="inner">
                            <h3 class="masthead-brand">Galgeleg</h3>
                            <nav>
                                <ul class="nav masthead-nav">
                                    <li><a href="game.jsp">Spil</a></li>
                                    <li class="active"><a href="#">Highscore</a></li>
                                    <li><a href="logout.jsp">Log ud</a></li>
                                </ul>
                            </nav>
                        </div>
                    </div>
                    </br>
                    <div class="inner cover">
                        <%
                            String currUser = (String) request.getSession().getAttribute("currUser");
                            if (currUser != null) {
                        %>
                        <h1 class="cover-heading">Highscore</h1>
                        <hr/> 

                        <table style="width:100%" class="lead">
                            <tr class="a">
                                <th>Plads</th>
                                <th>Navn</th>
                                <th>Antal forkerte</th> 
                                <th>Tid</th>
                            </tr>
                            <tr>
                                <td><img src="grafik/gold.png" width="20px" height="20px"/></td>
                                <td>Umais</td>
                                <td>3</td>
                                <td>05.02</td>
                            </tr>
                            <tr>
                                <td><img src="grafik/silver.png" width="20px" height="20px"/></td>
                                <td>Ahmad</td>
                                <td>5</td>
                                <td>02.04</td>
                            </tr>
                            <tr>
                                <td><img src="grafik/bronze.png" width="20px" height="20px"/></td>
                                <td>Samil</td>
                                <td>6</td>
                                <td>06.05</td>
                            </tr>
                            <tr class="notfirst">
                                <td></td>
                                <td>Samil</td>
                                <td>6</td>
                                <td>06.05</td>
                            </tr>
                            <tr class="notfirst">
                                <td></td>
                                <td>Samil</td>
                                <td>6</td>
                                <td>06.05</td>
                            </tr>
                            <tr class="notfirst">
                                <td></td>
                                <td>Samil</td>
                                <td>6</td>
                                <td>06.05</td>
                            </tr>
                        </table>

                        <hr/>
                    </div>  

                    <div class="mastfoot">
                        <div class="inner">
                            <p>Lavet af gruppe <a href="http://tourneo.dk/">TS</a></p>
                        </div
                    </div>

                </div>

            </div>

        </div>

        <% } else { %>
        <jsp:forward page = "index.jsp" />
        <% } %>

        <!-- <form method="post" action="start.jsp"><button type="submit" class="btn btn-primary">Login</button></form> -->
        <!-- Bootstrap core JavaScript
            ================================================== -->
        <!-- Placed at the end of the document so the pages load faster -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
        <script>window.jQuery || document.write('<script src="../../assets/js/vendor/jquery.min.js"><\/script>')</script>
        <script src="http://getbootstrap.com/dist/js/bootstrap.min.js"></script>
        <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
        <script src="http://getbootstrap.com/assets/js/ie10-viewport-bug-workaround.js"></script>
    </body> 
</html>