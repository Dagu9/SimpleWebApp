/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.guidolin.davide.servlets;

import it.guidolin.davide.utils.DBConnection;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author david
 */
@WebServlet(name = "modifyProfileServlet", urlPatterns = {"/modifyProfile"})
public class modifyProfileServlet extends HttpServlet {

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
        response.sendRedirect(request.getContextPath()+"/profile.jsp");
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
        
        String newEmail = request.getParameter("newEmail");
        String newUsername = request.getParameter("newUsername");
        
        String errorMessage = "";
        String modifyMessage = "";
        String destPage = "/profile.jsp";
        
        HttpSession sess = request.getSession();
        String oldEmail = "";
        if(sess!=null){
            oldEmail = (String)sess.getAttribute("email");
        } else {
            destPage = "/";
        }
        
        
        if(newEmail!=null && newEmail!="" && oldEmail!=""){
            try{
                PreparedStatement stmt = conn.prepareStatement("SELECT email FROM Users WHERE email=?");
                stmt.setString(1, newEmail);
                ResultSet rs = stmt.executeQuery();
                
                //new email doesn't exist in DB
                if(!rs.next()){
                    //Derby doesn't support ON UPDATE CASCADE
                    stmt = conn.prepareStatement("SELECT * FROM Users WHERE email=?");
                    stmt.setString(1, oldEmail);
                    
                    ResultSet rs1 = stmt.executeQuery();
                    String username = "";
                    String password = "";
                    if(rs1.next()){
                        username = rs1.getString("username");
                        password = rs1.getString("password");
                    }
                    
                    //Insert new record with new email
                    stmt = conn.prepareStatement("INSERT INTO Users(email,username,password) VALUES (?,?,?)");
                    stmt.setString(1, newEmail);
                    stmt.setString(2, username);
                    stmt.setString(3, password);
                    stmt.executeUpdate();
                    
                    //update post table foreign key
                    stmt = conn.prepareStatement("UPDATE Posts SET author=? WHERE author=?");
                    stmt.setString(1, newEmail);
                    stmt.setString(2, oldEmail);
                    stmt.executeUpdate();
                    
                    //delete old email record
                    stmt = conn.prepareStatement("DELETE FROM Users WHERE email=?");
                    stmt.setString(1, oldEmail);
                    stmt.executeUpdate();
                    
                    sess.removeAttribute("email");
                    sess.setAttribute("email", newEmail);
                    
                    modifyMessage = "Changes saved succesfully!";
                } else {
                    errorMessage = "Email already registered, use another email";
                }
                
            } catch (SQLException ex){
                ex.printStackTrace();
                errorMessage = "Error in saving changes, please try again";
            }
        }
        
        if(newUsername!=null && newUsername!="" && oldEmail!=""){
            try{

                PreparedStatement stmt = conn.prepareStatement("UPDATE Users SET username=? WHERE email=?");
                stmt.setString(1, newUsername);
                stmt.setString(2, oldEmail);

                stmt.executeUpdate();

                sess.removeAttribute("username");
                sess.setAttribute("username", newUsername);

                modifyMessage = "Changes saved succesfully!";

            } catch (SQLException ex){
                ex.printStackTrace();
                errorMessage = "Error in saving changes, please try again";
            }
        }
        
        if(!"".equals(errorMessage) && !"".equals(modifyMessage)){
            modifyMessage = "";
        }
        
        sess.setAttribute("errorMessage", errorMessage);
        sess.setAttribute("modifyMessage", modifyMessage);
        request.getRequestDispatcher(destPage).forward(request, response);
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
