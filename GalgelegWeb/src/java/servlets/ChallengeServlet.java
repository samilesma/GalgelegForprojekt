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
import java.sql.ResultSet;
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
        if (request.getParameter("type").equals("send")) {
            if (!currUser.equals(challenged)) {
                try {
                    spil.doit(Arrays.asList(currUser, "nulstil"));
                    ordet = spil.get(Arrays.asList(currUser, "getOrdet"));
                    spil.doit(Arrays.asList(currUser, "nulstil"));
                    
                } catch (Exception ex) {
                    Logger.getLogger(ChallengeServlet.class.getName()).log(Level.SEVERE, null, ex);
                }

                //ResultSet rs = con.select("SELECT challenges.id,word FROM challenges WHERE challenges.id = "+ challengeIDD + "");
                //ordet = rs.getString("word");
                f.challengeFriend(currUser, challenged, ordet);
                System.out.println("C: " + challenged);

                request.setAttribute("name",Integer.parseInt(request.getParameter("cid")) );
                request.getRequestDispatcher("page.jsp").forward(request, response);
            } else {
                System.out.println("SAMME BRUGER!");
                response.sendRedirect("challenges.jsp");
            }
        } else if (request.getParameter("type").equals("spil")) {
            int challengeIDD = Integer.parseInt(request.getParameter("cid"));
            System.out.println("11111111111111111");
            ResultSet rs = con.select("SELECT word FROM challenges WHERE challenges.id = " + challengeIDD + "");
            System.out.println("22222222222222222");
            String ord = rs.getString("word");
            System.out.println("33333333333333333");
            //spil.doit(arg0);
            System.out.println("4444444444444444444");
            response.sendRedirect("game.jsp");
            System.out.println("5555555555555555555");

        } else if (request.getParameter("type").equals("acceptdecline")) {

            if (request.getParameter("sub").equals("Accepter")) {
                int challengeID = Integer.parseInt(request.getParameter("id"));
                con.update("UPDATE challenges set acceptchl=1 WHERE id=" + challengeID + "");
                response.sendRedirect("challenges.jsp");
            } else if (request.getParameter("sub").equals("Afvis")) {
                int challengeID = Integer.parseInt(request.getParameter("id"));
                con.update("UPDATE challenges set acceptchl=-1 WHERE id=" + challengeID + "");
                response.sendRedirect("challenges.jsp");
            }

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
