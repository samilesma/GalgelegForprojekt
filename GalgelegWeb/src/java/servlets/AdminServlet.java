/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import utils.functions;

/**
 *
 * @author Samil
 */
public class AdminServlet extends HttpServlet {
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        
        String posted = request.getParameter("ban");
        String banned;
        String sid = posted.substring(posted.length() - 7);
        banned = posted.substring(0,posted.length()-7);
        functions f = new functions();
        System.out.println(sid.toString());
        
        long unixTime = System.currentTimeMillis() / 1000L;
        
        if(banned.equals("1day")){
            f.banUser(sid, unixTime+(24*60*60));
        }else if (banned.equals("1week")){
            f.banUser(sid,unixTime+(24*60*60*7));
        }else if(banned.equals("remadmin")){
            f.removeAdmin(sid);
        } else if(banned.equals("makadmin")){
            f.makeAdmin(sid);
        }else{
            f.banUser(sid,-1);
        }
        response.sendRedirect("admin.jsp");
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
            Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
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
