<%-- 
    Document   : feed
    Created on : 24-giu-2020, 10.16.52
    Author     : david
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true"%>

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
        <script type="text/javascript" src="scripts.js"></script>
    </head>
    <body class="bg-light text-dark">
        <nav class="navbar navbar-expand-sm sticky-top bg-dark navbar-dark">
            <div class="navbar-collapse collapse">
                <a class="navbar-brand" href="index.jsp">
                    <img src="https://www.randi.it/wp-content/uploads/2014/07/facebook-icon.png" alt="Logo" style="width:60px; height:60px;"/>
                </a>
                <ul class="navbar-nav">
                    <li class="nav-item">
                        <a class="nav-link" href="index.jsp"><h4>Facebook Lite</h4></a>
                    </li>
                </ul>
            </div>
            <div class="navbar-collapse">
                <ul class="navbar-nav ml-auto">
                    <li class="nav-item mr-4">
                        <div class="d-flex">
                            <a class="nav-link">Signed in as: </a>
                            <p class="nav-link text-white"><%= session.getAttribute("username") %></p>
                        </div>
                    </li>
                    <li class="nav-item active">
                        <a class="nav-link" href="<%= request.getContextPath()%>/feed.jsp">Feed</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="<%= request.getContextPath()%>/profile.jsp">Profile</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="<%= request.getContextPath()%>/logout">Logout</a>
                    </li>
                </ul>
            </div>
        </nav>
        <div class="container mt-3 w-50 mx-auto">
            <form action="<%= request.getContextPath()%>/newPost" method="POST" id="postForm">
                <div class="form-group">
                    <textarea id="contentArea" class="form-control" rows="3" required></textarea>
                    <input type="hidden" name="content" id="hiddenText">
                    <button onClick="submitForm()" class="mt-3 btn btn-primary btn-block">Post</button>
                </div>
            </form>
        </div>
        <div class="container mt-5 w-50 mx-auto" id="postContainer">

        </div>
    </body>
</html>
