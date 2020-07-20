/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.guidolin.davide.servlets;

import it.guidolin.davide.utils.DBConnection;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import javax.servlet.http.HttpSession;

/**
 *
 * @author david
 */
@WebServlet(name = "NewPostServlet", urlPatterns = {"/newPost"})
public class NewPostServlet extends HttpServlet {

    Connection conn = null;
    
    @Override
    public void init(){
        DBConnection dbCon = new DBConnection();
        conn = dbCon.getConnection();
    }
    
    @Override
    public void destroy(){
        try{
            conn.close();
        } catch (SQLException ex){
            ex.printStackTrace();
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
        response.setContentType("text/html;charset=UTF-8");
        response.sendRedirect(request.getContextPath()+"/");
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
        response.setContentType("text/html;charset=UTF-8");
        
        String destPage = "/feed.jsp";
        String author = "";
        String content = request.getParameter("content");
        HttpSession sess = request.getSession();
        
        boolean update = true;
        
        if(sess==null){
            destPage = "/";
            update = false;
        } else {
            author = (String)sess.getAttribute("email");
        }
        
        
        if(content==""){
            update = false;
        }
        
        Timestamp time = new Timestamp(System.currentTimeMillis());
        
        if(update){
            try{
                PreparedStatement stmt = conn.prepareStatement("INSERT INTO Posts Values(?,?,?)");
                stmt.setTimestamp(1, time);
                stmt.setString(2, author);
                stmt.setString(3, content);

                stmt.executeUpdate();

            } catch (SQLException ex){
                ex.printStackTrace();
            }
        }

        response.sendRedirect(request.getContextPath()+destPage);
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
