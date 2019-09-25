const crawler = require('./crawler')
var express = require('express');
var http = require('http');
var nic_ip = '192.168.1.71';
var bodyParser= require('body-parser');
var app = express();
/*
let users = [
  {
    name: '훈석이는 안드로이드 백엔드구요',
    link: '승재형은 안드로이드 프론트엔드구요',
    day: '저는 둘다에요'
  }
]
*/
app.get('/', (req, res) => {
  console.log("Start SCrapping");
  var id = "2015125054"
  var pw = "jooboo100@"
  var result = crawler.crawler(id,pw,function(result){
    res.send(result);
  })
});

var server = http.createServer(app).listen(8080,function(){
   console.log("익스프레스로 웹 서버를 실행함 : "+ "8080");
});
