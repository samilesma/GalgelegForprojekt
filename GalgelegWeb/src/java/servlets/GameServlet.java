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
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
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
public class GameServlet extends HttpServlet {

    src.GalgelogikService service = new src.GalgelogikService();
    src.GalgeI spil = service.getGalgelogikPort();
    ServletContext context = getServletContext();
    final String FILEPATH = context.getRealPath("/WEB-INF/highscore.txt");
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, JSONException {
        if (spil.erSpilletSlut()) {
            spil.nulstil();
            request.getSession().setAttribute("currTime",System.currentTimeMillis());
        } else {
            String letter = request.getParameter("letter");
            spil.gætBogstav(letter);
        }
        
        if (spil.erSpilletVundet()) {
            System.out.println("MUHAHHHAHAH");
            String navn = (String) request.getSession().getAttribute("currUser");
            int forkerte = spil.getAntalForkerteBogstaver();
            // int tid = request.getParameter("seconds");

            JSONArray hs = new JSONArray(Main.readFile(FILEPATH, StandardCharsets.UTF_8));
            Main.printHighscore(hs);
            if (Main.canAddHighscore(hs, forkerte, (int)((System.currentTimeMillis()-((long)request.getSession().getAttribute("currTime")))/1000))) {
                hs = Main.addHighscore(hs, navn, forkerte, (int)((System.currentTimeMillis()-((long)request.getSession().getAttribute("currTime")))/1000));
            } else {
                System.out.println("FALSE");
            }
            Main.printHighscore(hs);
        }
        response.sendRedirect("game.jsp");
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (JSONException ex) {
            Logger.getLogger(GameServlet.class.getName()).log(Level.SEVERE, null, ex);
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (JSONException ex) {
            Logger.getLogger(GameServlet.class.getName()).log(Level.SEVERE, null, ex);
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
