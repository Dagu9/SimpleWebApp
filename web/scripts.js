/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function submitForm(){
    $("#postForm").submit(function(){
        $("#hiddenText").val( encodeURI($("#contentArea").val()) );
    });
}

function loadPosts(){
    if(window.XMLHttpRequest){
        xhttp = new XMLHttpRequest();
    } else if (window.ActiveXObject){
        xhttp = new ActiveXObject("Microsoft.XMLHTTP");
    }
    
    xhttp.onreadystatechange = function(){
        if(this.readyState == 4 && this.status == 200){
            var data = this.responseText;
            
            var posts = JSON.parse(data);
            
            var html = "";
            for(var i=0; i<posts.length; i++){
                
                html += "<div class=\"card d-flex bg-primary my-3 text-white\"";
                
                html += "<div class=\"card-body d-flex\">"+
                            "<div class=\"card-title d-flex\">"+
                                "<h5 class=\"pl-2 pt-2 mr-auto\">"+decodeURI(posts[i]["author"])+"</h5>"+
                                "<h5 class=\"pr-2 pt-2 ml-auto\">"+decodeURI(posts[i]["creationTime"])+"</h5>"+
                            "</div>"+
                            "<div class=\"card-text d-flex\">"+
                                "<p class=\"pl-2 py-2\">"+decodeURI(posts[i]["content"])+"</p>"+
                            "</div>"+
                        "</div></div>";
            }
            
            $("#postContainer").fadeOut(100, function(){
                $(this).empty();
                $(this).append(html);
            }).fadeIn(500);
            //$(html).hide().appendTo("#postContainer").fadeIn(500);
        }
    };
    xhttp.open("POST","retrievePosts", true);
    xhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
    xhttp.send("timestamp="+(new Date()).getTime());
}


$(document).ready(function(){
    loadPosts();
    setInterval(loadPosts, 10000);
});