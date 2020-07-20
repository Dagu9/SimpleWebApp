/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.guidolin.davide.servlets;

import it.guidolin.davide.utils.DBConnection;
import it.guidolin.davide.utils.Post;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author david
 */
@WebServlet(name = "RetrievePosts", urlPatterns = {"/retrievePosts"})
public class RetrievePosts extends HttpServlet {
    
    Connection conn = null;
    
    @Override
    public void init(){
        DBConnection dbConn = new DBConnection();
        conn = dbConn.getConnection();
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
        response.setContentType("application/json; charset=utf-8");
        
        ArrayList<Post> posts = new ArrayList<Post>();
        PrintWriter out = response.getWriter();
        
        try{
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM Posts ORDER BY creationtime DESC FETCH FIRST 30 ROW ONLY";
            ResultSet rs = stmt.executeQuery(sql);
            
            PreparedStatement pst = conn.prepareStatement("SELECT username FROM Users WHERE email=?");
            while(rs.next()){
                pst.setString(1, rs.getString("author"));
                ResultSet rs1 = pst.executeQuery();
                String author = "";
                if(rs1.next()){
                    author = rs1.getString("username");
                }
                Post p = new Post(rs.getTimestamp("creationTime"), author, rs.getString("content"));
                posts.add(p);
            }
        } catch (SQLException ex){
            ex.printStackTrace();
            out.println("{}");
        }
        
        StringBuilder json = new StringBuilder();
        json.append("[");
        for(Post p:posts){
            LocalDateTime time = p.getCreationTime();
            int minutes = time.getMinute();
            int hour = time.getHour();
            int day = time.getDayOfMonth();
            int month = time.getMonthValue();
            int year = time.getYear();
            
            String obj = "{\"creationTime\":\""+String.format("%02d",day)+"/"+String.format("%02d",month)+"/"+String.format("%04d",year)+"  "+String.format("%02d",hour)+":"+String.format("%02d",minutes)+"\", \"author\":\""+p.getAuthor()+"\", \"content\":\""+p.getContent()+"\"},";
            json.append(obj);
        }
        
        json.deleteCharAt(json.length()-1);
        json.append("]");
        
        out.println(json);
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
