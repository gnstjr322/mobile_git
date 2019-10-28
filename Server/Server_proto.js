const crawler = require('./crawler')
const crawlerj = require('./crawlerj')
const crawlerc = require('./crawlerc')
var express = require('express');
var http = require('http');
var nic_ip = '192.168.1.71';
var bodyParser= require('body-parser');
var app = express();
var bodyParser = require("body-parser");
var path = require("path");
/*
let users = [
  {
    name: '훈석이는 안드로이드 백엔드구요',
    link: '승재형은 안드로이드 프론트엔드구요',
    day: '저는 둘다에요'
  }
]
*/
/*
app.get('/', (req, res) => {
  console.log("Start SCrapping");
  var id1 = req.query.id;//안드로이드 접속시 파리미터 id를 받는다
  var pw1 = req.query.pw;//안드로이드 접속시 파리미터 pw를 받는다
  console.log(id1);
  console.log(pw1);
  var id = "2015125054"
  var pw = "jooboo100@"
  var result = crawler.crawler(id,pw,function(result){
    res.json(result);
  })
});
*/
app.use(express.static(path.join(__dirname, 'public')));
app.use(bodyParser.urlencoded({ extended : false }));

app.post('/', (req, res) => {
  console.log("Start SCrapping");
  //var id1 = req.body.id;//안드로이드 접속시 파리미터 id를 받는다
  //var pw1 = req.body.pw;//안드로이드 접속시 파리미터 pw를 받는다
  //var aa = req.body;//안드로이드 접속시 파리미터 id를 받는다
  //var pw = req.param();//안드로이드 접속시 파리미터 pw를 받는다
  var id = req.param("id");
	var pw = req.param("pw");
  var num = req.param("num");
	//res.writeHead("200", {"Content-Type":"text/html;charset=utf8"});
	//res.write("user uId : " + uId);
	//res.write("<br>");
	//res.write("userd uPw : " + uPw);
	//res.write("<a href='/login_post.html'>login</a>");
	//res.end();
  /////
  console.log("id: "+id+"pw: "+ pw);
  //var id = "2015125054"
  //var pw = "jooboo100@"
  if(num==="0"){
    var result = crawler.crawler(id,pw,function(result){
      res.json(result);
    });
  }else if(num ==="1"){
    var result = crawlerj.crawlerj(id,pw,function(result){            //시험시간표 크롤링 장치
       res.json(result);
    });
  }else if(num ==="2"){
    var result = crawlerc.crawlerc(id,pw,function(result){            //시험시간표 크롤링 장치
       res.json(result);
    });
  }




});

var server = http.createServer(app).listen(8080,function(){
   console.log("익스프레스로 웹 서버를 실행함 : "+ "8080");
});
