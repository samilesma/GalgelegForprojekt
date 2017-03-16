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

    </head>
    <body>
        <%! src.GalgelogikService service = new src.GalgelogikService(); %>
        <%! src.GalgeI spil = service.getGalgelogikPort();%>
        
        <script>
            window.onload = function () {
                document.getElementById("letter").focus();
            };
        </script>
        
        <div class="site-wrapper">

            <div class="site-wrapper-inner">

                <div class="cover-container">

                    <div class="masthead clearfix">
                        <div class="inner">
                            <h3 class="masthead-brand">Galgeleg</h3>
                            <nav>
                                <ul class="nav masthead-nav">
                                    <li class="active"><a href="#">Spil</a></li>
                                    <li><a href="highscore">Highscore</a></li>
                                    <li><a href="logout.jsp">Log ud</a></li>
                                </ul>
                            </nav>
                        </div>
                    </div>

                    <div class="inner cover">
                        <%
                            String currUser = (String) request.getSession().getAttribute("currUser");
                            if (currUser != null) {
                        %>
                        <h1 class="cover-heading">Velkommen til Galgespillet <%=currUser%> </h1>  
                        <hr/>
                        <center>
                            <div class="row">
                                <div class="col-xs-6 col-md-3" id="hangmanpic">
                                    <img src=
                                         <%
                                             int errors = spil.getAntalForkerteBogstaver();
                                             if (errors >= 0 && errors <= 7) {
                                                 String picturePath;
                                                 picturePath = "grafik/forkert" + errors + ".png";
                                                 out.println(picturePath);
                                             }
                                         %> alt="Ikke indlæst">
                                </div>
                            </div>
                        </center>

                        <%
                            try {
                                java.lang.String synligtOrd = spil.getSynligtOrd();%>
                        <p class="lead">Ordet er: <%=synligtOrd%></p>
                        <% } catch (Exception ex) {

                            }

                            try {
                                java.util.List<java.lang.String> brugteBogstaver = spil.getBrugteBogstaver();%>
                        <p class="lead">Brugte bogstaver: <%=brugteBogstaver%></p>
                        <% } catch (Exception ex) {

                            }
                            try {
                                int numbErrors = spil.getAntalForkerteBogstaver();%>
                        <p class="lead">Antal fejl: <%=numbErrors%>/7</p>
                        <% } catch (Exception ex) {

                            }
                        %> 
                        <form action="GameServlet" method="post" >
                            <div class="form-group">
                                <label for="letter">Bogstav</label> <input
                                    type="text" class="form-control" name="letter" id="letter"
                                    placeholder="Indtast bogstav" required="required">
                            </div>
                            <button type="submit" class="btn btn-lg btn-primary" >Gæt!</button>
                        </form>
                        <hr/>
                    </div>

                    <%
                        try {
                            boolean result = spil.erSpilletTabt();
                            if (result) {
                                spil.nulstil();
                                response.sendRedirect("game.jsp"); %>
                    <!-- <script> document.getElementById("hangmanpic").src = 'grafik/tabt.png'</script> -->
                    <%

                            }
                        } catch (Exception ex) {
                            // TODO handle custom exceptions here
                        }

                        try {
                            boolean result = spil.erSpilletVundet();
                            if (result) {
                                spil.nulstil();
                                response.sendRedirect("game.jsp"); %>
                    <!-- <script> document.getElementById("hangmanpic").src = 'grafik/vundet.png' </script> -->
                    <% }
                        } catch (Exception ex) {
                            // TODO handle custom exceptions here
                        }
                    %>

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
        <% }%>

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