/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import com.google.gson.Gson;
import galgeleg.IllegalAccessException_Exception;
import galgeleg.InvocationTargetException_Exception;
import galgeleg.NoSuchMethodException_Exception;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import utils.connector;
import utils.functions;

/**
 *
 * @author Umais
 */
@WebServlet(name = "AndroidServlet", urlPatterns = {"/AndroidServlet"})
public class AndroidServlet extends HttpServlet {

    galgeleg.GalgelogikService service = new galgeleg.GalgelogikService();
    galgeleg.GalgeI spil = service.getGalgelogikPort();

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, JSONException, IllegalAccessException_Exception, InvocationTargetException_Exception, NoSuchMethodException_Exception, SQLException {
        PrintWriter out = response.getWriter();
        String type = request.getParameter("type");
        JSONObject returnObj = new JSONObject();
        connector con = new connector();

        switch (type) {
            case "erSidsteBogstavKorrekt":
                boolean sidsteBogstavVarKorrekt = spil.check(Arrays.asList(request.getParameter("sid"), "erSidsteBogstavKorrekt"));
                returnObj.put("sidsteBogstavVarKorrekt", sidsteBogstavVarKorrekt);
                break;
            case "erSpilletSlut":
                boolean spilletErSlut = spil.check(Arrays.asList(request.getParameter("sid"), "erSpilletSlut"));
                returnObj.put("spilletErSlut", spilletErSlut);
                break;
            case "erSpilletTabt":
                boolean spilletErTabt = spil.check(Arrays.asList(request.getParameter("sid"), "erSpilletTabt"));
                returnObj.put("spilletErTabt", spilletErTabt);
                break;
            case "erSpilletVundet":
                boolean spilletErVundet = spil.check(Arrays.asList(request.getParameter("sid"), "erSpilletVundet"));
                returnObj.put("spilletErVundet", spilletErVundet);
                break;
            case "getAntalForkerteBogstaver":
                int antalForkerteBogstaver = spil.getint(Arrays.asList(request.getParameter("sid"), "getAntalForkerteBogstaver"));
                returnObj.put("antalForkerteBogstaver", antalForkerteBogstaver);
                break;
            case "getBrugteBogstaver":
                List<String> brugteBogstaver = spil.getlist(Arrays.asList(request.getParameter("sid"), "getBrugteBogstaver"));
                returnObj.put("brugteBogstaver", brugteBogstaver);
                break;
            case "getOrdet":
                String ordet = spil.get(Arrays.asList(request.getParameter("sid"), "getOrdet"));
                returnObj.put("ordet", ordet);
                break;
            case "getSynligtOrd":
                String synligtOrd = spil.get(Arrays.asList(request.getParameter("sid"), "getSynligtOrd"));
                returnObj.put("synligtOrd", synligtOrd);
                break;
            case "gaetBogstav":
                String bogstav = request.getParameter("bogstav");
                spil.doit(Arrays.asList(request.getParameter("sid"), bogstav, "gaetBogstav"));
                break;
            case "gaet":
                String letter = request.getParameter("bogstav");
                String sid = request.getParameter("sid");
                String p = "";
                spil.doit(Arrays.asList(sid, letter, "gaetBogstav"));
                int forkerte = spil.getint(Arrays.asList(sid, "getAntalForkerteBogstaver"));
                int tid = (request.getSession().getAttribute("currTime")!=null?(int)((System.currentTimeMillis()-((long)request.getSession().getAttribute("currTime")))/1000):Integer.parseInt(request.getParameter("time")));
                if(spil.check(Arrays.asList(sid,"erSpilletSlut"))) {
                    if(request.getSession().getAttribute("chall") != null) {
                        ResultSet rs=con.select("SELECT p1 FROM challenges WHERE id="+request.getSession().getAttribute("chall"));
                        rs.next();
                        p=(rs.getString("p1").equals(request.getParameter("sid"))?"1":"2");
                        con.update("UPDATE multiplayer SET wrong"+p+"="+forkerte+", time"+p+"="+tid+" WHERE cid="+request.getSession().getAttribute("chall"));
                        
                        p=(p.equals("1")?"2":"1");
                        rs=con.select("SELECT wrong1,time1,wrong2,time2 FROM multiplayer WHERE cid="+request.getSession().getAttribute("chall"));
                        rs.next();
                        if(rs.getInt("wrong1")!=-1 && rs.getInt("wrong2")!=-1) {
                            int w;
                            if(rs.getInt("wrong1")<rs.getInt("wrong2") || (rs.getInt("wrong1")==rs.getInt("wrong2") && rs.getInt("time1")<rs.getInt("time2"))) w=1;
                            else if(rs.getInt("wrong2")<rs.getInt("wrong1") || (rs.getInt("wrong2")==rs.getInt("wrong1") && rs.getInt("time2")<rs.getInt("time1"))) w=2;
                            else w=3;
                            
                            con.update("UPDATE challenges SET win="+w+" WHERE cid="+request.getSession().getAttribute("chall"));
                        }
                    }
                    if(forkerte!=7) {
                        con.update("INSERT INTO singleplayer (sid,wrong,time,timestamp) VALUES ('" + sid + "','" + forkerte + "','" + tid + "','" + System.currentTimeMillis() / 1000L + "')");
                        returnObj.put("type", 1);
                    }
                    else {
                        returnObj.put("type", 0);
                    }
                }
                Gson gson = new Gson();
                returnObj.put("antalForkerteBogstaver", forkerte);
                returnObj.put("brugteBogstaver", new JSONArray(gson.toJson(spil.getlist(Arrays.asList(sid, "getBrugteBogstaver")))));
                returnObj.put("synligtOrd", spil.get(Arrays.asList(sid, "getSynligtOrd")));
                returnObj.put("ordet", spil.get(Arrays.asList(sid, "getOrdet")));
                break;
            case "hentBruger":
                String name = request.getParameter("username");
                String pass = request.getParameter("password");

                if (spil.hentBruger(name, pass)) {
                    String fuldenavn = spil.get(Arrays.asList(name, "hentNavn"));

                    returnObj.put("error", false);
                    returnObj.put("fullname", fuldenavn);
                } else {
                    returnObj.put("error", true);
                }
                break;
            case "hentNavn":
                String fuldenavn = spil.get(Arrays.asList(request.getParameter("sid"), "hentNavn"));
                returnObj.put("fuldenavn", fuldenavn);
                break;
            case "getMuligeOrd":
                JSONArray muligeOrd = new JSONArray(spil.get(Arrays.asList(request.getParameter("sid"), "getMuligeOrd")));
                returnObj.put("muligeOrd", muligeOrd);
                break;
            case "nulstil":
                spil.doit(Arrays.asList(request.getParameter("sid"), "nulstil"));
                returnObj.put("synligtOrd", spil.get(Arrays.asList(request.getParameter("sid"), "getSynligtOrd")));
                if(request.getSession().getAttribute("currUser")!=null) {
                    request.getSession().setAttribute("currTime",System.currentTimeMillis());
                    returnObj.put("nulstill", true);
                }
                break;
            case "opdaterSynligtOrd":
                spil.doit(Arrays.asList(request.getParameter("sid"), "opdaterSynligtOrd"));
                break;
            case "getHighscores":
                ResultSet rs = con.select("SELECT singleplayer.sid, wrong, time, name, surname, timestamp FROM singleplayer LEFT JOIN users ON (singleplayer.sid = users.sid) ORDER BY wrong,time,timestamp LIMIT 10");
                JSONArray jsonArray = new JSONArray();
                int i = 0;
                while (rs.next()) {
                    i++;
                    String fullname = rs.getString("name") + rs.getString("surname");
                    int wrong = rs.getInt("wrong");
                    int time = rs.getInt("time");
                    Timestamp stamp = new Timestamp(rs.getInt("timestamp") * 1000L);
                    Date currdate = new Date(stamp.getTime());
                    Format format = new SimpleDateFormat("dd/MM-yyyy HH:mm");
                    String date = format.format(currdate);
                    jsonArray.put(new JSONObject().put("fullname", fullname).put("wrong", wrong).put("time", time).put("date", date));
                }
                returnObj.put("highscores", jsonArray);
                break;
            case "chat":
                functions f=new functions();
                returnObj=f.getMessagesJson(request.getParameter("sid"),Long.parseLong(request.getParameter("timestamp")));
                break;
            case "setOrdet":
                String str = request.getParameter("str");
                spil.doit(Arrays.asList(request.getParameter("sid"),str,"setOrdet"));
                break;
        }
        out.println(returnObj.toString());
    }
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (JSONException ex) {
            Logger.getLogger(AndroidServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException_Exception ex) {
            Logger.getLogger(AndroidServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException_Exception ex) {
            Logger.getLogger(AndroidServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchMethodException_Exception ex) {
            Logger.getLogger(AndroidServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(AndroidServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (JSONException ex) {
            Logger.getLogger(AndroidServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException_Exception ex) {
            Logger.getLogger(AndroidServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException_Exception ex) {
            Logger.getLogger(AndroidServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchMethodException_Exception ex) {
            Logger.getLogger(AndroidServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(AndroidServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
