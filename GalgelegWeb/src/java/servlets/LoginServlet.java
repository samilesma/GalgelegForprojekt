/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package servlets;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import utils.connector;

/**
 *
 * @author Umais
 */
public class LoginServlet extends HttpServlet {
    
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
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("username");
        String pass = request.getParameter("password");
        String redirect ="";
        
        System.out.println(name);
        System.out.println(pass);
        
        if (spil.hentBruger(name, pass)) {
            
            connector con=new connector();
            ResultSet rs;
            try {
                rs = con.select("SELECT admin FROM users WHERE sid='"+name+"'");
                rs.next();
                request.getSession().setAttribute("currAdmin",(rs.getInt("admin")==1?true:false));
                request.getSession().setAttribute("currUser",name);
                request.getSession().setAttribute("currName",spil.hentNavn());
                request.getSession().setAttribute("currTime",System.currentTimeMillis());
            } catch (SQLException ex) {
                Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
                
                request.getSession().setAttribute("currAdmin",false);
                request.getSession().setAttribute("currUser",name);
                request.getSession().setAttribute("currName",spil.hentNavn());
                request.getSession().setAttribute("currTime",System.currentTimeMillis());
            }
            redirect ="game.jsp";
            spil.nulstil();
        } else {//if name&pass not match then it display error page//
            redirect = "error.jsp";
        }
        response.sendRedirect(redirect);
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
        processRequest(request,response);
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
        processRequest(request, response);
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
