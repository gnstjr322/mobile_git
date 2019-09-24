/*jslint devel: true */
/* eslint-disable no-console */
/*eslint no-undef: "error"*/
/*eslint-env node*/
//IP주소가 변화하면 안드로이드 앱 내에 있는 url 주소도 바꿔주어야 정상 동작하기시작함!
/*
var fs = require('fs');
var http = require('http');
var url = require('url');
var ROOT_DIR = "html/";
http.createServer(function (req, res) {
  var urlObj = url.parse(req.url, true, false);
  fs.readFile(ROOT_DIR + urlObj.pathname, function (err,data) {
    if (err) {
      res.writeHead(404);
      res.end(JSON.stringify(err));
      return;
    }
    res.writeHead(200);
    res.end(data);
  });
}).listen(8080);


*/
/*
var http = require('http');
var server = http.createServer();


var port = 3000;
var nic_ip = '192.168.1.71';


server.listen(port, nic_ip, 30000, function() {
    console.log('192.168.1.71:3000 으로 서버 시작');
});

server.on('connection', function(socket) {
    console.log('클라이언트 정보 - ip : %s, port : %d', socket.remoteAddress, socket.remotePort);
  });
  */
// HTTP 모듈 추출
/*
var http = require('http');

// 서버 생성
var server = http.createServer();

// connection 이벤트 설정
// 클라이언트에서 http://127.0.0.1.3:3000으로 접속하면
// connection 이벤트가 발생하며 callback 함수 실행
server.on('connection', function() {
     console.log()
});
*/
/*
var port = 3000
var ip = '192.168.1.71'

var http = require('http');
var serve = http.createServer(function (req, res){
  console.log("Start");
    var jsonData;
    req.on('data', function (chunk){
        jsonData = chunk;
        console.log(jsonData);
    });
    req.on('end', function(){
        var reqObj = JSON.parse(jsonData);
        res.writeHead(200);
        res.end(JSON.stringify(resObj));
    });
})
serve.listen(ip,port,30000,function(){
  console.log("서버시작")
});
*/

//출처: https://chaeyoungdo.tistory.com/18 [YoungDo]
/*
var http = require('http');
var messages = [
  'Hello World',
  'From a basic Node.js server',
  'Take Luck'];
http.createServer(function (req, res) {
  res.setHeader("Content-Type", "text/html");
  res.writeHead(200);
  res.write('<html><head><title>Simple HTTP Server</title></head>');
  res.write('<body>');
  for (var idx in  messages){
    res.write('\n<h1>' + messages[idx] + '</h1>');
  }
  res.end('\n</body></html>');
}).listen(8080);
*/
/*
var http = require('http');
var options = {
    hostname: '192.168.1.71',
    port: '8080',
    //path: '/hello.html'
  };
function handleResponse(response) {
  var serverData = '';
  response.on('data', function (chunk) {
    serverData += chunk;
  });
  response.on('end', function () {
    console.log(serverData);
  });
}
http.request(options, function(response){
  handleResponse(response);
}).end();

*/

var express = require('express');
var http = require('http');
var nic_ip = '192.168.1.71';
var bodyParser= require('body-parser');
var app = express();

app.set('port',process.env.PORT || 3000);
app.use(bodyParser.urlencoded({extended:false}));
app.use(bodyParser.json());

//첫 번째 미들웨어
app.use(function(req, res, next) {

    console.log('첫 번째 미들웨어 호출 됨');
    var approve ='send complete';


    var paramId = req;
    //var paramPassword = req.body.password;
    console.log('request : '+paramId);

    //아이디 일치여부 flag json 데이터입니다.

    res.send(approve);

});

var server = http.createServer(app).listen(app.get('port'),function(){
   console.log("익스프레스로 웹 서버를 실행함 : "+ app.get('port'));
});
