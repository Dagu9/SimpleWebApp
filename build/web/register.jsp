<%-- 
    Document   : register
    Created on : 24-giu-2020, 9.57.09
    Author     : david
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
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
                    <p class="text-danger">${registerMessage}</p>
                </div>
            </div>
            <div class="row">
                <div class="needs-validation col d-flex justify-content-center text-center">
                    <form action="<%= request.getContextPath()%>/register" method="POST">
                        <div class="form-group">
                            <label for="email">Email:</label>
                            <input type="text" name="email" class="form-control" required>
                        </div>
                        <div class="form-group">
                            <label for="username">Username:</label>
                            <input type="text" name="username" class="form-control" required>
                        </div>
                        <div class="form-group">
                            <label for="password">Password:</label>
                            <input type="password" name="password" class="form-control" required>
                        </div>
                        <input type="submit" value="Register" class="btn btn-primary btn-block">
                    </form>
                </div>
            </div>
            <div class="row mt-5">
                <div class="col d-flex justify-content-center">
                    <h6>Already Registered?</h6>
                </div>
            </div>
            <div class="row">
                <div class="col d-flex justify-content-center">
                    <a href="<%= request.getContextPath()%>/index.jsp" class="btn btn-primary">Login</a>
                </div>
            </div>
        </div>
        
    </body>
</html>
