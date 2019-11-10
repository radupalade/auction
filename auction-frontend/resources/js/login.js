$(document).ready(function () {
    $.get("http://localhost:8080/api/register", function(data, status){
        alert("Data: " + data + "\nStatus: " + status);
    });
});


/*
$(document).ready(function () {

    var xmlhttp = new XMLHttpRequest();   // new HttpRequest instance
    var theUrl = "http://localhost:8080/api/test";
    xmlhttp.open("POST", theUrl);
    xmlhttp.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    xmlhttp.send(JSON.stringify({ email: "hello@user.com"}));*/
