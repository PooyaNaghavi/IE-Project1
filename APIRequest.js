var XMLHttpRequest = require("xmlhttprequest").XMLHttpRequest;

var XMLHttpRequest = require("xmlhttprequest").XMLHttpRequest;

function requestGet(url, callback) {
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            callback(xhttp.responseText);
        }
    }
    xhttp.open("GET", url, true);
    xhttp.send();
}

function requestPost(url, params, callback) {
    var xhttp = new XMLHttpRequest();
    xhttp.open("POST", url, true);
    xhttp.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
    xhttp.onreadystatechange = function () {
        console.log("now", this.readyState, this.status)
        if (this.readyState == 4 && this.status == 200) {
            callback(xhttp.responseText);
        }else if(this.readyState == 4){
            callback(xhttp.responseText);
        }
    }

    var body = null;
    for(var key in params) {
        if(!body) {
            body = key + "=" + params[key]
        } else {
            body += "&" + key + "=" + params[key]
        }
    }
    console.log(body)
    xhttp.send(body);

}

requestPost("http://localhost:8080/skill", {
    skill : "CSS",
    user : 1
}, function (res) {
    // res = JSON.parse(res)
    console.log(baseUrl)
    console.log(res);
    console.log("--------------------------------")
})

requestGet("http://localhost:8080/project", function (res) {
    // res = JSON.parse(res)
    console.log(res)
    console.log("--------------------------------")
})



//run in localhost:8080//run in localhost:8080