<%-- 
    Document   : profile
    Created on : 25-giu-2020, 9.51.15
    Author     : david
--%>

<%@page import="java.net.URI"%>
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
        <script type="text/javascript" src="scripts.js"></script>
        
        <script>
            
            function showTextInputField(btnId){
                
                var id;
                var inputName = "";
                var textField = "";
                
                if(btnId == "btnEmail"){
                    id = "#emailDiv";
                    inputName = "newEmail";
                    textField = "<label for=\""+inputName+"\">New Email</label><input class=\"form-control\" id=\""+inputName+"\" type=\"email\" name=\""+inputName+"\" form=\"modifyForm\" >";
                } else if (btnId == "btnUsername"){
                    id = "#usernameDiv";
                    inputName = "newUsername";
                    textField = "<label for=\""+inputName+"\">New Username</label><input class=\"form-control\" id=\""+inputName+"\" type=\"text\" name=\""+inputName+"\" form=\"modifyForm\" >";
                }
                
                 
                
                $(id).fadeOut("fast", function(){
                    $(this).empty();
                    $(this).append(textField);
                }).fadeIn("fast");
                $("#"+inputName).focus();
            }
            
        </script>
    </head>
    <%  String referer = new URI(request.getHeader("referer")).getPath();
    
        if(!(referer.equals(request.getContextPath()+"/modifyProfile")||referer.equals(request.getContextPath()+"/profile.jsp"))){
            session.removeAttribute("errorMessage");
            session.removeAttribute("modifyMessage");
        }
       %>
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
                    <li class="nav-item mr-3">
                        <div class="d-flex">
                            <a class="nav-link">Signed in as: </a>
                            <p class="nav-link text-white"><%= session.getAttribute("username") %></p>
                        </div>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="<%= request.getContextPath()%>/feed.jsp">Feed</a>
                    </li>
                    <li class="nav-item active">
                        <a class="nav-link" href="<%= request.getContextPath()%>/profile.jsp">Profile</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="<%= request.getContextPath()%>/logout">Logout</a>
                    </li>
                </ul>
            </div>
        </nav>
        <form action="<%= request.getContextPath()%>/modifyProfile" method="POST" id="modifyForm"></form>
        <div class="container-fluid mt-4 w-50 mx-auto">
            <div class="card d-flex my-3">
                <div class="d-flex card-body">
                    <div class="mr-auto">
                        <div class="card-title">
                            <h4>Email</h4>
                        </div>
                        <div class="card-text d-flex">
                            <p class="mr-auto"><%= session.getAttribute("email")%></p>
                        </div>
                    </div>
                    <div class="my-auto" id="emailDiv">
                        <button id="btnEmail" onClick="showTextInputField(this.id)" class="btn btn-primary ml-auto">Change</button>
                    </div>
                </div>
            </div>
            <div class="card d-flex my-3">
                <div class="d-flex card-body">
                    <div class="mr-auto">
                        <div class="card-title">
                            <h4>Username</h4>
                        </div>
                        <div class="card-text">
                            <p><%= session.getAttribute("username")%></p>
                        </div>
                    </div>
                    <div class="my-auto" id="usernameDiv">
                        <button id="btnUsername" onClick="showTextInputField(this.id)" class="btn btn-primary ml-auto">Change</button>
                    </div>
                </div>
            </div>
            <div class="container-fluid w-50 mx-auto d-flex justify-content-center">
                <input form="modifyForm" type="submit" value="Save Changes" class="btn btn-primary">
            </div>
            <div class="container-fluid w-50 mx-auto d-flex justify-content-center">
                <p class="text-danger py-2">${errorMessage}</p>
                <p class="text-success py-2">${modifyMessage}</p>
            </div>
        </div>
    </body>
</html>
