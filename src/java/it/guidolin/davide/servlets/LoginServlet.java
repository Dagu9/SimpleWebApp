/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.guidolin.davide.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import it.guidolin.davide.utils.DBConnection;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.http.HttpSession;

/**
 *
 * @author david
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {
    
    Connection conn = null;
    
    @Override
    public void init(){
        DBConnection dbConn = new DBConnection();
        conn = dbConn.getConnection();
    }

    @Override
    public void destroy(){
        try {
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public String login(String email, String password){
        String username = "";
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT email,username,password FROM Users WHERE email = ?");
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            
            if(rs.next()){
                MessageDigest md = MessageDigest.getInstance("SHA-256");
                byte[] digest = md.digest(password.getBytes(StandardCharsets.UTF_8));
                String hash = toHexString(digest);
                
                if(hash.equals(rs.getString("password"))){
                    username = rs.getString("username");
                }
            }            
        } catch(SQLException | NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
        
        return username;
    }
    
    public static String toHexString(byte[] hash) { 
        // Convert byte array into signum representation  
        BigInteger number = new BigInteger(1, hash);  
  
        // Convert message digest into hex value  
        StringBuilder hexString = new StringBuilder(number.toString(16));  
  
        // Pad with leading zeros 
        while (hexString.length() < 32)  
        {  
            hexString.insert(0, '0');  
        }  
  
        return hexString.toString();  
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
        response.setContentType("text/html; charset=utf-8");
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
        response.setContentType("text/html; charset=utf-8");
        
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        
        String destPage = "/index.jsp";
        String username = login(email,password);
        if(!"".equals(username)){
            HttpSession session = request.getSession();
            session.setMaxInactiveInterval(60*10);
            session.setAttribute("email", email);
            session.setAttribute("username", username);
            destPage = "/feed.jsp";
        } else {
            String message = "Invalid username or password";
            request.setAttribute("message", message);
        }
        
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
