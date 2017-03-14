<%-- 
    Document   : index
    Created on : 13-03-2017, 16:28:30
    Author     : Umais
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!-- Bootstrap core CSS -->
        <link href="http://getbootstrap.com/dist/css/bootstrap.min.css" rel="stylesheet">
        <!-- Custom styles for this template -->
        <link href="cover.css" rel="stylesheet">

        <title>Galgeleg</title>
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
                                    <li class="active"><a href="">Spil</a></li>
                                    <li><a href="highscore">Highscore</a></li>
                                    <li class="active"><a href="">Log ud</a></li>
                                </ul>
                            </nav>
                        </div>
                    </div>
                    <div class="inner cover">
                        <h1 class="cover-heading">Du logges ud...</h1>
                        <% request.getSession().removeAttribute("currUser"); 
                        request.getSession().invalidate();
                        %>
                        <jsp:forward page="index.jsp"/>
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
