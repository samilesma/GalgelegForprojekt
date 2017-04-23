/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONException;
import org.json.JSONObject;

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
            throws ServletException, IOException, JSONException {
        PrintWriter out = response.getWriter();
        String type = request.getParameter("type");

        JSONObject returnObj = new JSONObject();
        
        switch (type) {
            case "erSidsteBogstavKorrekt":
                boolean sidsteBogstavVarKorrekt = spil.erSidsteBogstavKorrekt();
                returnObj.put("sidsteBogstavVarKorrekt", sidsteBogstavVarKorrekt);
                break;
            case "erSpilletSlut":
                boolean spilletErSlut = spil.erSpilletSlut();
                returnObj.put("spilletErSlut", spilletErSlut);
                break;
            case "erSpilletTabt":
                boolean spilletErTabt = spil.erSpilletTabt();
                returnObj.put("spilletErTabt", spilletErTabt);
                break;
            case "erSpilletVundet":
                boolean spilletErVundet = spil.erSpilletVundet();
                returnObj.put("spilletErVundet", spilletErVundet);
                break;
            case "getAntalForkerteBogstaver":
                int antalForkerteBogstaver = spil.getAntalForkerteBogstaver();
                returnObj.put("antalForkerteBogstaver", antalForkerteBogstaver);
                break;
            case "getBrugteBogstaver":
                List<String> brugteBogstaver = spil.getBrugteBogstaver();
                returnObj.put("brugteBogstaver", brugteBogstaver);
                break;
            case "getOrdet":
                String ordet = spil.getOrdet();
                returnObj.put("ordet", ordet);
                break;
            case "getSynligtOrd":
                String synligtOrd = spil.getSynligtOrd();
                returnObj.put("synligtOrd", synligtOrd);
                break;
            case "gætBogstav":
                String bogstav = request.getParameter("bogstav");
                spil.gætBogstav(bogstav);
                break;    
            case "hentBruger":
                String name = request.getParameter("username");
                String pass = request.getParameter("password");

                if (spil.hentBruger(name, pass)) {
                    String fuldenavn = spil.hentNavn();

                    returnObj.put("error", false);
                    returnObj.put("fullname", fuldenavn);
                } else {
                    returnObj.put("error", true);
                }
                break;
            case "hentNavn":
                String fuldenavn = spil.hentNavn();
                returnObj.put("fuldenavn", fuldenavn);
                break;
            case "getMuligeOrd":
                List<String> muligeOrd = spil.getMuligeOrd();
                returnObj.put("muligeOrd", muligeOrd);
                break;
            case "nulstil":
                spil.nulstil();
                break;
            case "opdaterSynligtOrd":
                spil.opdaterSynligtOrd();
                break;
            case "setOrdet":
                String iString = request.getParameter("i");
                int i = Integer.parseInt(iString);
                spil.setOrdet(i);
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
