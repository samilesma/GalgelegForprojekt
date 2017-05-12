<%-- 
    Document   : testjsp
    Created on : 13-03-2017, 20:03:13
    Author     : Umais
--%>
<%@page import="java.util.Arrays"%>
<%@page import="java.util.List"%>
<%
if(session.getAttribute("currUser") == null || session.getAttribute("currUser").equals("")) response.sendRedirect("index.jsp");
String currUser = (String) request.getSession().getAttribute("currUser");
String currName = (String) request.getSession().getAttribute("currName");
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
		<link rel="icon" type="image/x-icon" href="/favicon.ico"/>
		<title>Galgeleg</title>
		<!-- <p class="navbar-text navbar-right">Signed in as <a href="#" class="navbar-link">Mark Otto</a></p> -->
	</head>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
         <script src="ajax.js"></script>
        <script>
        function escapeHtml(text) {
            return text.replace(/&/g, "&amp;").replace(/</g, "&lt;").replace(/>/g, "&gt;").replace(/"/g, "&quot;").replace(/'/g, "&#039;");
        }
        
        function setMsg(msg,sid,time)
        {
            jQuery("div#chatbox").append("<div class='mesg'><div class='name'>"+sid+":<span style='margin-left:1em'> </span></div><div class='msg'>"+escapeHtml(msg)+"</div></div><br/>");
            jQuery("#chatbox").scrollTop($("#chatbox").prop("scrollHeight"));
        
        }
        
        jQuery("document").ready(function(){
            jQuery("form").submit(function(){
                setMsg($(this).find("#usermsg").val(),"<%=currName%>",new Date() / 1000 | 0);
                jQuery("#usermsg").val("");
            });
        });
        
        setInterval(function(){
            var date = (new Date() / 1000 | 0)-3;
            jQuery.ajax({
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
		<% galgeleg.GalgelogikService service = new galgeleg.GalgelogikService(); %>
		<% galgeleg.GalgeI spil = service.getGalgelogikPort(); %>
		<% String ordet = spil.get(Arrays.asList(currUser,"getOrdet")); %>

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
									<%
									boolean currAdmin = (boolean) request.getSession().getAttribute("currAdmin");
									if(currAdmin) { %> <li><a href="admin.jsp">Admin</a></li> <% } %>
									<li class="active"><a href="#">Spil</a></li>
									<li><a href="chat.jsp">Chat</a></li>
                                                                        <li><a href="challenges.jsp">Udfordringer</a></li>
									<li><a href="highscore.jsp">Highscore</a></li>
									<li><a href="logout.jsp">Log ud</a></li>
								</ul>
							</nav>
						</div>
					</div>
					<br/>
					<div class="inner cover ts-content">
                                            <%
						if (currUser != null) {
						%>
						<h2 class="cover-heading">Velkommen til Galgespillet <%=currName%> </h2>  
						<hr/>
                                            <div class="col-xs-6">
						
						<div class="row">
							<div class="col-xs-12">
								<img id="hangmanpic" src=
								<%
								int errors = spil.getint(Arrays.asList(currUser,"getAntalForkerteBogstaver"));
								if (errors >= 0 && errors <= 7) {
									String picturePath;
									picturePath = "grafik/forkert" + errors + ".png";
									out.println(picturePath);
								}
								%> alt="Ikke indlæst">
							</div>
						</div>
						<p class="lead" id="finalMessage"><b><b></p>
						<%
						try {
							String synligtOrd = spil.get(Arrays.asList(currUser,"getSynligtOrd"));%>
							<p id="ordetLabel" class="lead"><%=spil.get(Arrays.asList(currUser,"getOrdet"))%> Ordet er: <%=synligtOrd%></p>
							<%
						} catch (Exception ex) {
							
						}
						
						try {
							List<String> brugteBogstaver = spil.getlist(Arrays.asList(currUser,"getBrugteBogstaver"));
                                                        %>
							<p class="lead">Brugte bogstaver: <%=brugteBogstaver%></p>
							<%
						} catch (Exception ex) {
							
						}
						
						try {
							int numbErrors = spil.getint(Arrays.asList(currUser,"getAntalForkerteBogstaver"));
                                                        %>
							<p class="lead">Antal fejl: <%=numbErrors%>/7</p>
							<%
						} catch (Exception ex) {
							
						}
						%>
						<form action="GameServlet" method="post" id="guessForm">
							<div class="form-group">
								<label for="letter">Bogstav</label>
								<input type="text" class="form-control" name="letter" id="letter" placeholder="Indtast bogstav" required="required" />
							</div>
							<button type="submit" id="btnGuess" class="btn btn-lg btn-primary" >Gæt!</button>
						</form>
						<form action="GameServlet" method="post" id="newGameForm" hidden="true">
							<button type="submit" class="btn btn-lg btn-primary">Nyt spil</button>
						</form>
						<hr/>
					
					<%
					try {
						boolean result = spil.check(Arrays.asList(currUser,"erSidsteBogstavKorrekt"));
						if (!result && !(spil.getlist(Arrays.asList(currUser,"getBrugteBogstaver")).isEmpty())) {
							%> 
							<div class="alert alert-danger" role="alert">Forkert gæt!</div>
							<%
						}
						else if (result && !(spil.getlist(Arrays.asList(currUser,"getBrugteBogstaver")).isEmpty())) {
							%> 
							<div class="alert alert-success" role="alert">Korrekt gæt!</div>
							<%
						}
					} catch (Exception ex) {
						
					}
					
					try {
						boolean result = spil.check(Arrays.asList(currUser,"erSpilletTabt"));
						if (result) {
							%>
							<script>
							document.getElementById("hangmanpic").src = 'grafik/tabt.png'
							document.getElementById("finalMessage").innerHTML = "Øv du har tabt!"
							document.getElementById("finalMessage").style.color = 'red'
							document.getElementById("ordetLabel").innerHTML = "Ordet var <%=ordet%>"
							document.getElementById("guessForm").hidden = true;
							document.getElementById("newGameForm").hidden = false;
							</script>
							<%
						}
					} catch (Exception ex) {
						
					}
					
					try {
						boolean result = spil.check(Arrays.asList(currUser,"erSpilletVundet"));
						if (result) {
							%>
							<script>
							document.getElementById("hangmanpic").src = 'grafik/vundet.png'
							document.getElementById("finalMessage").innerHTML = "Tillykke du har vundet!"
							document.getElementById("finalMessage").style.color = 'green'
							document.getElementById("guessForm").hidden = true;
							document.getElementById("newGameForm").hidden = false;
							</script>
							<%
						}
					} catch (Exception ex) {
						
					}
					%>
					
				</div>
                                        <div class="col-xs-6">
                   <div id="chatbox" style="width:100%; height:375px; margin:auto; border:2px solid #929391; border-radius:20px;"></div>
                    <br/>
                    <form action="ChatServlet" method="post" class="ajax">
                        <input name="usermsg"  type="text" class="form-control" id="usermsg" style="margin-bottom:15px;"/>
                        <button type="submit" class="btn btn-lg btn-primary" style="width:100px;">Send</button>
                    </form>
                                        </div>
                                        
                            
                                        <div class="clearfix"></div>
                                        <div class="mastfoots">
						<div class="inners">
							<p>Lavet af gruppe <a href="http://tourneo.dk/">TS</a></p>
						</div>
					</div>
			</div>
                </div>
		</div>
		<%
	}
	else {
		%>
		<jsp:forward page = "index.jsp" />
		<%
	}
	%>
	<!-- <form method="post" action="start.jsp"><button type="submit" class="btn btn-primary">Login</button></form> -->
	<!-- Bootstrap core JavaScript
		================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
	
	<script>window.jQuery || document.write('<script src="../../assets/js/vendor/jquery.min.js"><\/script>')</script>
	<script src="http://getbootstrap.com/dist/js/bootstrap.min.js"></script>
	<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
	<script src="http://getbootstrap.com/assets/js/ie10-viewport-bug-workaround.js"></script>
	
</html>