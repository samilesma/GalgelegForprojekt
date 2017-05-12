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
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, InvocationTargetException_Exception, NoSuchMethodException_Exception, IllegalAccessException_Exception {
        String name = request.getParameter("username");
        String pass = request.getParameter("password");
        
        System.out.println(name);
        System.out.println(pass);
        
        if (spil.hentBruger(name.toLowerCase(), pass)) {
            connector con=new connector();
            ResultSet rs;
            try {
                rs = con.select("SELECT admin FROM users WHERE sid='"+name+"'");
                con.update("UPDATE users SET password=? WHERE sid='"+name+"'",new String[]{pass});
                rs.next();
                request.getSession().setAttribute("currAdmin",(rs.getInt("admin")==1?true:false));
                request.getSession().setAttribute("currUser",name);
                request.getSession().setAttribute("currName",spil.get(Arrays.asList(name,"hentNavn")));
                request.getSession().setAttribute("currTime",System.currentTimeMillis());
            } catch (SQLException ex) {
                System.out.println("SQLEXCEPTION!!!!!!!");
            }
            spil.doit(Arrays.asList(name,"nulstil"));
            response.sendRedirect("game.jsp");
        } else {
            response.sendRedirect("error.jsp");
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            processRequest(request,response);
        } catch (InvocationTargetException_Exception ex) {
            Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchMethodException_Exception ex) {
            Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException_Exception ex) {
            Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            processRequest(request,response);
        } catch (InvocationTargetException_Exception ex) {
            Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchMethodException_Exception ex) {
            Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException_Exception ex) {
            Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
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
