/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/8.5.11
 * Generated at: 2017-04-24 08:05:41 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class game_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {

 galgeleg.GalgelogikService service = new galgeleg.GalgelogikService(); 
 galgeleg.GalgeI spil = service.getGalgelogikPort();
 java.lang.String ordet = spil.getOrdet(); 
  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  private static final java.util.Set<java.lang.String> _jspx_imports_packages;

  private static final java.util.Set<java.lang.String> _jspx_imports_classes;

  static {
    _jspx_imports_packages = new java.util.HashSet<>();
    _jspx_imports_packages.add("javax.servlet");
    _jspx_imports_packages.add("javax.servlet.http");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_classes = null;
  }

  private volatile javax.el.ExpressionFactory _el_expressionfactory;
  private volatile org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public java.util.Set<java.lang.String> getPackageImports() {
    return _jspx_imports_packages;
  }

  public java.util.Set<java.lang.String> getClassImports() {
    return _jspx_imports_classes;
  }

  public javax.el.ExpressionFactory _jsp_getExpressionFactory() {
    if (_el_expressionfactory == null) {
      synchronized (this) {
        if (_el_expressionfactory == null) {
          _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
        }
      }
    }
    return _el_expressionfactory;
  }

  public org.apache.tomcat.InstanceManager _jsp_getInstanceManager() {
    if (_jsp_instancemanager == null) {
      synchronized (this) {
        if (_jsp_instancemanager == null) {
          _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
        }
      }
    }
    return _jsp_instancemanager;
  }

  public void _jspInit() {
  }

  public void _jspDestroy() {
  }

  public void _jspService(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response)
      throws java.io.IOException, javax.servlet.ServletException {

    final java.lang.String _jspx_method = request.getMethod();
    if (!"GET".equals(_jspx_method) && !"POST".equals(_jspx_method) && !"HEAD".equals(_jspx_method) && !javax.servlet.DispatcherType.ERROR.equals(request.getDispatcherType())) {
      response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "JSPs only permit GET POST or HEAD");
      return;
    }

    final javax.servlet.jsp.PageContext pageContext;
    javax.servlet.http.HttpSession session = null;
    final javax.servlet.ServletContext application;
    final javax.servlet.ServletConfig config;
    javax.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    javax.servlet.jsp.JspWriter _jspx_out = null;
    javax.servlet.jsp.PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html;charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write('\n');

if(session.getAttribute("currUser") == null || session.getAttribute("currUser").equals("")) response.sendRedirect("index.jsp");

      out.write("\n");
      out.write("\n");
      out.write("<!DOCTYPE html>\n");
      out.write("<html>\n");
      out.write("    <head>\n");
      out.write("        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n");
      out.write("        <meta name=\"viewport\" content=\"width = device-width, initial-scale = 1\">\n");
      out.write("        <!-- Bootstrap core CSS -->\n");
      out.write("        <link href=\"http://getbootstrap.com/dist/css/bootstrap.min.css\" rel=\"stylesheet\">\n");
      out.write("        <!-- Custom styles for this template -->\n");
      out.write("        <link href=\"cover.css\" rel=\"stylesheet\">\n");
      out.write("        <title>Galgeleg</title>\n");
      out.write("        <!-- <p class=\"navbar-text navbar-right\">Signed in as <a href=\"#\" class=\"navbar-link\">Mark Otto</a></p> -->\n");
      out.write("    </head>\n");
      out.write("    <body>\n");
      out.write("        ");
      out.write("\n");
      out.write("        ");
      out.write("\n");
      out.write("        ");
      out.write("\n");
      out.write("\n");
      out.write("        <script>\n");
      out.write("            window.onload = function () {\n");
      out.write("                document.getElementById(\"letter\").focus();\n");
      out.write("            };\n");
      out.write("        </script>\n");
      out.write("\n");
      out.write("        <div class=\"site-wrapper\">\n");
      out.write("\n");
      out.write("            <div class=\"site-wrapper-inner\">\n");
      out.write("\n");
      out.write("                <div class=\"cover-container\">\n");
      out.write("\n");
      out.write("                    <div class=\"masthead clearfix\">\n");
      out.write("                        <div class=\"inner\">\n");
      out.write("                            <h3 class=\"masthead-brand\">Galgeleg</h3>\n");
      out.write("                            <nav>\n");
      out.write("                                <ul class=\"nav masthead-nav\">\n");
      out.write("                                    ");

                                    boolean currAdmin = (boolean) request.getSession().getAttribute("currAdmin");
                                    if(currAdmin) { 
      out.write(" <li><a href=\"admin.jsp\">Admin</a></li> ");
 } 
      out.write("\n");
      out.write("                                    <li class=\"active\"><a href=\"#\">Spil</a></li>\n");
      out.write("                                    <li><a href=\"chat.jsp\">Chat</a></li>\n");
      out.write("                                    <li><a href=\"highscore.jsp\">Highscore</a></li>\n");
      out.write("                                    <li><a href=\"logout.jsp\">Log ud</a></li>\n");
      out.write("                                </ul>\n");
      out.write("                            </nav>\n");
      out.write("                        </div>\n");
      out.write("                    </div>\n");
      out.write("                    <br/>\n");
      out.write("                    <div class=\"inner cover\">\n");
      out.write("                        ");

                            String currUser = (String) request.getSession().getAttribute("currUser");
                            String currName = (String) request.getSession().getAttribute("currName");
                            if (currUser != null) {
                        
      out.write("\n");
      out.write("                        <h1 class=\"cover-heading\">Velkommen til Galgespillet ");
      out.print(currName);
      out.write(" </h1>  \n");
      out.write("                        <hr/>\n");
      out.write("\n");
      out.write("                        <div class=\"row\">\n");
      out.write("\n");
      out.write("                            <div class=\"col-xs-6 col-md-3\">\n");
      out.write("                                <img id=\"hangmanpic\" src=\n");
      out.write("                                         ");

                                             int errors = spil.getAntalForkerteBogstaver();
                                             if (errors >= 0 && errors <= 7) {
                                                 String picturePath;
                                                 picturePath = "grafik/forkert" + errors + ".png";
                                                 out.println(picturePath);
                                             }
                                         
      out.write(" alt=\"Ikke indlæst\">\n");
      out.write("                            </div>\n");
      out.write("\n");
      out.write("                        </div>\n");
      out.write("\n");
      out.write("                                <p class=\"lead\" id=\"finalMessage\"><b><b></p>\n");
      out.write("\n");
      out.write("                        ");

                            try {
                                java.lang.String synligtOrd = spil.getSynligtOrd();
      out.write("\n");
      out.write("                        <p id=\"ordetLabel\" class=\"lead\">");
      out.print(spil.getOrdet());
      out.write(" Ordet er: ");
      out.print(synligtOrd);
      out.write("</p>\n");
      out.write("                        ");
 } catch (Exception ex) {

                            }

                            try {
                                java.util.List<java.lang.String> brugteBogstaver = spil.getBrugteBogstaver();
      out.write("\n");
      out.write("                        <p class=\"lead\">Brugte bogstaver: ");
      out.print(brugteBogstaver);
      out.write("</p>\n");
      out.write("                        ");
 } catch (Exception ex) {

                            }
                            try {
                                int numbErrors = spil.getAntalForkerteBogstaver();
      out.write("\n");
      out.write("                        <p class=\"lead\">Antal fejl: ");
      out.print(numbErrors);
      out.write("/7</p>\n");
      out.write("                        ");
 } catch (Exception ex) {

                            }
                        
      out.write("\n");
      out.write("                        <form action=\"GameServlet\" method=\"post\" id=\"guessForm\">\n");
      out.write("                            <div class=\"form-group\">\n");
      out.write("                                <label for=\"letter\">Bogstav</label> <input\n");
      out.write("                                    type=\"text\" class=\"form-control\" name=\"letter\" id=\"letter\"\n");
      out.write("                                    placeholder=\"Indtast bogstav\" required=\"required\">\n");
      out.write("                            </div>\n");
      out.write("                            <button type=\"submit\" id=\"btnGuess\" class=\"btn btn-lg btn-primary\" >Gæt!</button>\n");
      out.write("\n");
      out.write("                        </form>\n");
      out.write("                        <form action=\"GameServlet\" method=\"post\" id=\"newGameForm\" hidden=\"true\">\n");
      out.write("                            <button type=\"submit\" class=\"btn btn-lg btn-primary\">Nyt spil</button>\n");
      out.write("                        </form>\n");
      out.write("                        <hr/>\n");
      out.write("                    </div>\n");
      out.write("                    ");

                        try {
                            boolean result = spil.erSidsteBogstavKorrekt();
                            if (!result && !(spil.getBrugteBogstaver().isEmpty())) { 
      out.write(" \n");
      out.write("                    <div class=\"alert alert-danger\" role=\"alert\">Forkert gæt!</div>\n");
      out.write("                    ");
      } else if (result && !(spil.getBrugteBogstaver().isEmpty())) { 
      out.write(" \n");
      out.write("                    <div class=\"alert alert-success\" role=\"alert\">Korrekt gæt!</div>\n");
      out.write("                    ");
 }
                        } catch (Exception ex) {
                        }

                        try {
                            boolean result = spil.erSpilletTabt();
                            if (result) {
                                // spil.nulstil();
                                // response.sendRedirect("game.jsp");


                    
      out.write("\n");
      out.write("                    \n");
      out.write("                    <script>\n");
      out.write("                        document.getElementById(\"hangmanpic\").src = 'grafik/tabt.png'\n");
      out.write("                        document.getElementById(\"finalMessage\").innerHTML = \"Øv du har tabt!\"\n");
      out.write("                        document.getElementById(\"finalMessage\").style.color = 'red'\n");
      out.write("                        document.getElementById(\"ordetLabel\").innerHTML = \"Ordet var ");
      out.print(ordet);
      out.write("\"\n");
      out.write("                        document.getElementById(\"guessForm\").hidden = true;\n");
      out.write("                        document.getElementById(\"newGameForm\").hidden = false;\n");
      out.write("                    </script>\n");
      out.write("                    \n");
      out.write("                    ");
                            }
                        } catch (Exception ex) {
                        }
// spil.nulstil();
                        // response.sendRedirect("game.jsp");
                        try {
                            boolean result = spil.erSpilletVundet();
                            if (result) {
                                
                    
      out.write("\n");
      out.write("                    <script>\n");
      out.write("                        document.getElementById(\"hangmanpic\").src = 'grafik/vundet.png'\n");
      out.write("                        document.getElementById(\"finalMessage\").innerHTML = \"Tillykke du har vundet!\"\n");
      out.write("                        document.getElementById(\"finalMessage\").style.color = 'green'\n");
      out.write("                        document.getElementById(\"guessForm\").hidden = true;\n");
      out.write("                        document.getElementById(\"newGameForm\").hidden = false;\n");
      out.write("                    </script>\n");
      out.write("                    ");
 }
                        } catch (Exception ex) {
                            // TODO handle custom exceptions here
                        }
                    
      out.write("\n");
      out.write("\n");
      out.write("                    <div class=\"mastfoot\">\n");
      out.write("                        <div class=\"inner\">\n");
      out.write("                            <p>Lavet af gruppe <a href=\"http://tourneo.dk/\">TS</a></p>\n");
      out.write("                        </div\n");
      out.write("                    </div>\n");
      out.write("\n");
      out.write("                </div>\n");
      out.write("\n");
      out.write("            </div>\n");
      out.write("\n");
      out.write("        </div>\n");
      out.write("\n");
      out.write("        ");
 } else { 
      out.write("\n");
      out.write("        ");
      if (true) {
        _jspx_page_context.forward("index.jsp");
        return;
      }
      out.write("\n");
      out.write("        ");
 }
      out.write("\n");
      out.write("\n");
      out.write("        <!-- <form method=\"post\" action=\"start.jsp\"><button type=\"submit\" class=\"btn btn-primary\">Login</button></form> -->\n");
      out.write("        <!-- Bootstrap core JavaScript\n");
      out.write("            ================================================== -->\n");
      out.write("        <!-- Placed at the end of the document so the pages load faster -->\n");
      out.write("        <script src=\"https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js\"></script>\n");
      out.write("        <script>window.jQuery || document.write('<script src=\"../../assets/js/vendor/jquery.min.js\"><\\/script>')</script>\n");
      out.write("        <script src=\"http://getbootstrap.com/dist/js/bootstrap.min.js\"></script>\n");
      out.write("        <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->\n");
      out.write("        <script src=\"http://getbootstrap.com/assets/js/ie10-viewport-bug-workaround.js\"></script>\n");
      out.write("    </body> \n");
      out.write("</html>");
    } catch (java.lang.Throwable t) {
      if (!(t instanceof javax.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try {
            if (response.isCommitted()) {
              out.flush();
            } else {
              out.clearBuffer();
            }
          } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
