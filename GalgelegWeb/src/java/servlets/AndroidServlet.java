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
import java.sql.SQLException;
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
                int forkerte = spil.getint(Arrays.asList(sid, "getAntalForkerteBogstaver"));
                spil.doit(Arrays.asList(sid, letter, "gaetBogstav"));
                if (spil.check(Arrays.asList(sid, "erSpilletVundet"))) {
                    int tid = Integer.parseInt(request.getParameter("time"));
                    connector con = new connector();
                    con.update("INSERT INTO singleplayer (sid,wrong,time,timestamp) VALUES ('" + sid + "','" + forkerte + "','" + tid + "','" + System.currentTimeMillis() / 1000L + "')");
                    returnObj.put("type", 1);
                }
                else if (spil.check(Arrays.asList(sid, "erSpilletTabt"))) {
                    returnObj.put("type", 0);
                }
                Gson gson = new Gson();
                returnObj.put("antalForkerteBogstaver", forkerte);
                returnObj.put("brugteBogstaver", new JSONArray(gson.toJson(spil.getlist(Arrays.asList(sid, "getBrugteBogstaver")))));
                returnObj.put("synligtOrd", spil.get(Arrays.asList(sid, "getSynligtOrd")));
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
                break;
            case "opdaterSynligtOrd":
                spil.doit(Arrays.asList(request.getParameter("sid"), "opdaterSynligtOrd"));
                break;
            /* 
            case "setOrdet":
                String iString = request.getParameter("i");
                int i = Integer.parseInt(iString);
                spil.doit(Arrays.asList(request.getParameter("sid"),i,"setOrdet"));
                break;
             */
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
