/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONException;
import utils.Main;

/**
 *
 * @author Umais
 */
public class HighscoreServlet extends HttpServlet {

    galgeleg.GalgelogikService service = new galgeleg.GalgelogikService();
    galgeleg.GalgeI spil = service.getGalgelogikPort();
    final static String FILEPATH = "C:\\Users\\ahmad\\Desktop\\Opgaver\\4 - Semester\\Distribuerede systemer\\GalgelegForprojekt\\highscore.txt";

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
        
        System.out.println("MUHAHHHAHAH");
        String navn = (String) request.getSession().getAttribute("currUser");
        int forkerte = spil.getAntalForkerteBogstaver();
        // int tid = request.getParameter("seconds");

        JSONArray hs = new JSONArray(Main.readFile(FILEPATH, StandardCharsets.UTF_8));
        Main.printHighscore(hs);
        if (Main.canAddHighscore(hs, forkerte, 5)) {
            hs = Main.addHighscore(hs, navn, forkerte, 5);
        } else {
            System.out.println("FALSE");
        }
        Main.printHighscore(hs);

//        RequestDispatcher rd = request.getRequestDispatcher("game.jsp");
//        rd.forward(request, response);
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
            Logger.getLogger(HighscoreServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(HighscoreServlet.class.getName()).log(Level.SEVERE, null, ex);
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
