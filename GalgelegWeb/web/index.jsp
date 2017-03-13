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
        <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
        <style>
            /*body {
                    background-color: #5DAB5D
            }*/
            .jumbotron{
                background-color: #5DAB5D;
                color:white;
                margin-top: 50px
            }
            /* Adds borders for tabs */
            .tab-content {
                border-left: 1px solid #ddd;
                border-right: 1px solid #ddd;
                border-bottom: 1px solid #ddd;
                padding: 10px;
            }
            .nav-tabs {
                margin-bottom: 0;
            }
        </style>
        <title>JSP Page</title>
    </head>

    <script>
        function login(){
            
        }
        
    </script>
    
    <body>
        <%-- start web service invocation --%><hr/>
        <%! src.GalgeI spil;%>
        <%
            try {
                src.GalgelogikService service = new src.GalgelogikService();
                spil = service.getGalgelogikPort();
                spil.nulstil();
                out.print(spil.getOrdet());
            } catch (Exception ex) {
                // TODO handle custom exceptions here
            }
        %>
        <%-- end web service invocation --%><hr/>
    <center>
        <hr/>
        <h3>Indtast dit DTU brugernavn og password</h3>
        <hr/>
        <hr/>
        <label for="username">Brugernavn</label>
        <input id="username" type="text" />
        <br>
        <label for="password">Adgangskode</label>
        <input id="password" type="password" />
        <br>
        <hr/>
        <button type="button" class="btn btn-lg btn-primary" onClick="login();">Login</button>    
        <%-- start web service invocation --%><hr/>
        <%
            
            try {
                boolean result = spil.hentBruger(request.getParameter("username"), request.getParameter("password"));
                if (result) {
                    String redirectURL = "www.facebook.com";
                    response.sendRedirect(redirectURL);
                } else {
                    out.print("TAg dig sammen!");
                }
            } catch (Exception ex) {
                // TODO handle custom exceptions here
            }
        %>
        <%-- end web service invocation --%><hr/>

        <br>
    </center>

</body>
</html>
