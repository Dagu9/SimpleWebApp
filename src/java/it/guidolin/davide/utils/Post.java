/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.guidolin.davide.utils;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 *
 * @author david
 */
public class Post {
    private LocalDateTime creationTime;
    private String author;
    private String content;
    
    public Post(Timestamp ts, String author, String content){
        this.creationTime = ts.toLocalDateTime();
        this.author = author;
        this.content = content;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    
    
}
