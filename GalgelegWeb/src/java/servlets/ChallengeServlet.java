/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import galgeleg.IllegalAccessException_Exception;
import galgeleg.InvocationTargetException_Exception;
import galgeleg.NoSuchMethodException_Exception;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import utils.connector;
import utils.functions;

/**
 *
 * @author Tolga
 */
public class ChallengeServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        functions f = new functions();
        galgeleg.GalgelogikService service = new galgeleg.GalgelogikService(); 
        galgeleg.GalgeI spil = service.getGalgelogikPort();
        String currUser = (String) request.getSession().getAttribute("currUser");
        String challenged = request.getParameter("sid");
        String ordet = "";
        connector con = new connector();
        functions function = new functions();
        int challengeID =  Integer.parseInt(request.getParameter("id"));
        con.update("UPDATE challenges set acceptchl=1 WHERE id="+challengeID+"");
        if(!currUser.equals(challenged)){
        try {
            spil.doit(Arrays.asList(currUser,"nulstil"));
            ordet = spil.get(Arrays.asList(currUser,"getOrdet"));
        } catch (Exception ex) {
            Logger.getLogger(ChallengeServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        

        System.out.println("C: "+challenged);
        
        f.challengeFriend(currUser, challenged, ordet);
        
        response.sendRedirect("game.jsp");
        }
        
        else{
        System.out.println("SAMME BRUGER!");
        response.sendRedirect("challenges.jsp");
    }

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
        } catch (SQLException ex) {
            Logger.getLogger(ChallengeServlet.class.getName()).log(Level.SEVERE, null, ex);
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
        } catch (SQLException ex) {
            Logger.getLogger(ChallengeServlet.class.getName()).log(Level.SEVERE, null, ex);
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
