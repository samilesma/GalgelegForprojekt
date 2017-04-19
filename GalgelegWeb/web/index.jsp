<%-- 
    Document   : index
    Created on : 13-03-2017, 16:28:30
    Author     : Umais
--%>
<%
if(session.getAttribute("currUser") != null && !session.getAttribute("currUser").equals("")) response.sendRedirect("game.jsp");
%>
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
    </head>
    <body>
        <%! src.GalgelogikService service = new src.GalgelogikService(); %>
        <%! src.GalgeI spil = service.getGalgelogikPort();%>
        
        <script>
            window.onload = function () {
                document.getElementById("username").focus();
            };
        </script>
        
        <div class="site-wrapper">

            <div class="site-wrapper-inner">

                <div class="cover-container">

                    <div class="masthead clearfix">
                        <div class="inner">
                            <h3 class="masthead-brand">Galgeleg</h3>
                        </div>
                    </div>
                    <div class="inner cover">
                        <h1 class="cover-heading">Velkommen til Galgeleg!</h1>
                        <h2 class="cover-heading">Login med dit DTU login for at fortsætte</h2>
                        <hr/>
                        <form action="LoginServlet" method="post" >
                            <div class="form-group">
                                <label for="username">Studienummer</label> <input
                                    type="text" class="form-control" name="username" id="username"
                                    placeholder="Indtast studienummer" required="required">

                            </div>
                            <div class="form-group">
                                <label for="password">Password</label> <input
                                    type="password" class="form-control" name="password" id="password"
                                    placeholder="Indtast adgangskode" required="required">
                            </div>
                            <button type="submit" class="btn btn-lg btn-primary" >Login</button>
                        </form>
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
