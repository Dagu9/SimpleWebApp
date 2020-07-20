/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.guidolin.davide.servlets;

import it.guidolin.davide.utils.DBConnection;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author david
 */
@WebServlet(name = "RegisterServlet", urlPatterns = {"/register"})
public class RegisterServlet extends HttpServlet {
        
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
    
    public boolean register(String email, String username, String password){
        boolean status = false;
        
        try{
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Users WHERE email = ?");
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if(!rs.next()){
                
                PreparedStatement stmtInsert = conn.prepareStatement("INSERT INTO Users(email,username,password) VALUES(?,?,?)");
               
                MessageDigest md = MessageDigest.getInstance("SHA-256");
                byte[] digest = md.digest(password.getBytes(StandardCharsets.UTF_8));
                String hash = toHexString(digest);
                
                stmtInsert.setString(1, email);
                stmtInsert.setString(2, username);
                stmtInsert.setString(3, hash);
                
                stmtInsert.executeUpdate();
                status = true;
            }
        } catch(SQLException | NoSuchAlgorithmException ex){
            ex.printStackTrace();
        }
        
        return status;
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
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        String destPage = "/index.jsp";
        String registerMessage;
        if(register(email,username,password)){
            registerMessage = "Your registration was succesful, now login please";
        } else {
            registerMessage = "Email already registered, please login or try another email";
            destPage = "/register.jsp";
        }
        
        request.setAttribute("registerMessage", registerMessage);
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
