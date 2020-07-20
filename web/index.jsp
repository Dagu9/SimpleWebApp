<%-- 
    Document   : index
    Created on : 24-giu-2020, 9.13.32
    Author     : david
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<html>
    <head>
        <title>Facebook Lite</title>
        <link rel="shortcut icon" href="https://www.randi.it/wp-content/uploads/2014/07/facebook-icon.png"/>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <!-- Latest compiled and minified CSS -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
        <!-- jQuery library -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <!-- Popper JS -->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
        <!-- Latest compiled JavaScript -->
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
    </head>
    <body class="bg-dark text-light">
        <nav class="navbar navbar-expand-sm justify-content-center">
            <ul class="navbar-nav">
                <li class="nav-item">
                    <a class="navbar-brand" href="index.jsp">
                        <img height="110" width="110" class="img-fluid" src="https://images-eu.ssl-images-amazon.com/images/I/21-leKb-zsL.png"/>
                    </a>
                </li>
                <li class="nav-item">
                    <h1 class="display-2">Facebook Lite</h1>
                </li>
            </ul>
        </nav>
        <div class="container-fluid" style="position:absolute; top:30%">
            <div class="row">
                <div class="col d-flex justify-content-center">
                    <p class="text-danger">${message}</p>
                </div>
            </div>
            <div class="row">
                <div class="col d-flex justify-content-center">
                    <p class="text-success">${registerMessage}</p>
                </div>
            </div>
            <div class="row">
                <div class="col d-flex justify-content-center text-center">
                    <form action="<%= request.getContextPath()%>/login" method="POST">
                        <div class="form-group">
                            <label for="email">Email:</label>
                            <input type="email" name="email" class="form-control" required>
                        </div>
                        <div class="form-group">
                            <label for="password">Password:</label>
                            <input type="password" name="password" class="form-control" required>
                        </div>
                        <input type="submit" value="Submit" class="btn btn-primary btn-block">
                    </form>
                </div>
            </div>
            <div class="row mt-5">
                <div class="col d-flex justify-content-center">
                    <h6>Not registered?</h6>
                </div>
            </div>
            <div class="row">
                <div class="col d-flex justify-content-center">
                    <a href="<%= request.getContextPath()%>/register.jsp" class="btn btn-primary">Register</a>
                </div>
            </div>
        </div>
    </body>
</html>
