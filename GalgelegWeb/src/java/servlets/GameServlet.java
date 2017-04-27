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
import java.sql.SQLException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONException;
import utils.connector;

/**
 *
 * @author Umais
 */
public class GameServlet extends HttpServlet {

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
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, JSONException, SQLException, IllegalAccessException_Exception, InvocationTargetException_Exception, NoSuchMethodException_Exception {
        if (spil.check(Arrays.asList((String)request.getSession().getAttribute("currUser"),"erSpilletSlut"))) {
            spil.doit(Arrays.asList((String)request.getSession().getAttribute("currUser"),"nulstil"));
            request.getSession().setAttribute("currTime",System.currentTimeMillis());
        } else {
            String letter = request.getParameter("letter");
            spil.doit(Arrays.asList((String)request.getSession().getAttribute("currUser"),letter,"gætBogstav"));
        }
        
        if (spil.check(Arrays.asList((String)request.getSession().getAttribute("currUser"),"erSpilletVundet"))) {
            System.out.println("MUHAHHHAHAH");
            String navn = (String) request.getSession().getAttribute("currUser");
            int forkerte = spil.getint(Arrays.asList((String)request.getSession().getAttribute("currUser"),"getAntalForkerteBogstaver"));
            int tid=(int)((System.currentTimeMillis()-((long)request.getSession().getAttribute("currTime")))/1000);
            connector con=new connector();
            con.update("INSERT INTO singleplayer (sid,wrong,time,timestamp) VALUES ('"+navn+"','"+forkerte+"','"+tid+"','"+System.currentTimeMillis() / 1000L+"')");
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
        } catch (SQLException ex) {
            Logger.getLogger(GameServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException_Exception ex) {
            Logger.getLogger(GameServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException_Exception ex) {
            Logger.getLogger(GameServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchMethodException_Exception ex) {
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
        } catch (SQLException ex) {
            Logger.getLogger(GameServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException_Exception ex) {
            Logger.getLogger(GameServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException_Exception ex) {
            Logger.getLogger(GameServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchMethodException_Exception ex) {
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
