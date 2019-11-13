const crawler = require('./crawler')
const crawlerj = require('./crawlerj')
const crawlerc = require('./crawlerc')
const crawlers = require('./crawlers')
var express = require('express');
var http = require('http');
var nic_ip = '192.168.1.71';
var bodyParser= require('body-parser');
var app = express();
var bodyParser = require("body-parser");
var path = require("path");
app.use(express.static(path.join(__dirname, 'public')));
app.use(bodyParser.urlencoded({ extended : false }));

app.post('/', (req, res) => {
  console.log("Start SCrapping");

  var id = req.param("id");
	var pw = req.param("pw");
  var num = req.param("num");
  var link = req.param("link");

  console.log("id: "+id+"pw: "+ pw);

  if(num==="0"){
    var result = crawler.crawler(id,pw,function(result){
      res.json(result);
    });
  }else if(num ==="1"){
    var result = crawlerj.crawlerj(id,pw,function(result){            //시험시간표 크롤링 장치
       res.json(result);
    });
  }else if(num ==="2"){
    var result = crawlerc.crawlerc(id,pw,link,function(result){            //시험시간표 크롤링 장치
       res.json(result);
    });
  }else if(num ==="3"){
    var result = crawlers.crawlers(id,pw,function(result){            //시험시간표 크롤링 장치
       res.json(result);
    });
  }




});

var server = http.createServer(app).listen(8080,function(){
   console.log("익스프레스로 웹 서버를 실행함 : "+ "8080");
});
