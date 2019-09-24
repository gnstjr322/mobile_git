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

/*
var express = require('express');
var app = express();

app.get('/',function(req,res){
  console.log("Connection");
  res.send('Connection');
})

app.listen(3000,function(){
  console.log("서버기동");
})
*/

var express = require('express');
var http = require('http');
var nic_ip = '192.168.1.71';
var bodyParser= require('body-parser');
var app = express();

//app.set('port',process.env.PORT || 8080);
//app.use(bodyParser.urlencoded({extended:false}));
//app.use(bodyParser.json());

//첫 번째 미들웨어
app.get('/', (req, res) => {
  console.log('crawling start');
  const puppeteer = require('puppeteer');
  const cheerio = require('cheerio');

  function delay( timeout ) {
    return new Promise(( resolve ) => {
      setTimeout( resolve, timeout );
    });
  }

  const url = 'https://lms.kau.ac.kr/login.php';

  (async () => {
     function extractNewsData(html,html2) {
       const $ = cheerio.load(html);
       const $2 = cheerio.load(html2);
       var data = new Array()
       $('#page-blocks > div.block.block-upcomming.block-coursemos > div.content > ul').each(function (index, ele) {
           var temp = Object()
           temp['name'] = $(this).find('li> a > div.title').text().trim()
           temp['link'] = $(this).find('li > a').attr('href')
           data.push(temp)
       })
       $('#div > div.media').each(function (index, ele) {
         var temp = Object()
         temp['name'] = $(this).find('a > div.media-body > h5.media-heading').text().trim()
         temp['link'] = $(this).find('a').attr('href')
         //temp['day'] = $(this).find('a > div.media-body >  p.timeago').text().trim()
         data.push(temp)
       })
       $2('div > div.media').each(function (index, ele) {
           var temp = Object()
           temp['name'] = $2(this).find('a > div.media-body > h4.media-heading').text().trim()
           temp['link'] = $2(this).find('a').attr('href')
           temp['day'] = $2(this).find('a > div.media-body >  p.timeago').text().trim()
           data.push(temp)
       })
       console.log(data);
       // res.json(data);
       res.send(data[0])


      return data;
     }
        // 브라우저 옵션 설정
        const browserOption = {
          //slowMo: 500, // 디버깅용으로 퍼핏티어 작업을 지정된 시간(ms)만큼 늦춥니다.
          headless: false // 디버깅용으로 false 지정하면 브라우저가 자동으로 열린다.
        };

        // 1. 브라우저를 띄운다. => 브라우저 객체 생성
        const browser = await puppeteer.launch(browserOption);

        // 2. 페이지를 띄운다. => 페이지 객체 생성
        const page = await browser.newPage();


        let response;
        try {
          // 리다이렉트 되는 페이지의 주소를 사용.


          // 탭 옵션
          const pageOption = {
            // waitUntil: 적어도 500ms 동안 두 개 이상의 네트워크 연결이 없으면 탐색이 완료된 것으로 간주합니다.
            waitUntil: 'networkidle2',
            // timeout: 20초 안에 새 탭의 주소로 이동하지 않으면 에러 발생
            timeout: 20000
          };

          // 3. 새 탭에 뉴스 기사 주소를 입력해서 접속한다.
          response = await page.goto(url, pageOption);
          await page.type( "div.textform >  input#input-username", "2015125054" ); // 아이디 입력
        	await page.type( "div.textform >  input#input-password", "jooboo100@" ); // 패스워드 입력
          await delay(3000);
          const elementHandle = await page.waitFor( "input" ); // 인풋
          await elementHandle.press( "Enter" );  // 입력
          await delay(5000);
        } catch (error) {
          await page.close();
          await browser.close();
          console.error(error);
          return;
        }
        let html;
        try {
          html = await page.content();
        } catch (error) {
          console.error(error);
          return;
        }
        let html2
        try{
          await delay(3000);
          await page.click( "#page-blocks > div.block.block-notification.block-coursemos > div.content.ubnotification > div.more_button > a" );// 모두보기 click
          await delay(3000);
          html2 = await page.content();
        }catch{
          await page.close();
          await browser.close();
          console.error(error);
          return;
        } finally {
          await page.close();
          // 7. 브라우저 닫기
          await browser.close();
        }
        const newsData = extractNewsData(html,html2);
        // 크롤링 결과
        console.log(newsData);

        console.log('get');
        //return newsData
  })();
  //res.send('hello Route');

});
app.use(function(req, res, next) {

    console.log('첫 번째 미들웨어 호출 됨');
    var approve ='send complete';


    var paramId = req;
    //var paramPassword = req.body.password;
    console.log('request : '+paramId);

    //아이디 일치여부 flag json 데이터입니다.

    res.send(approve);

});

var server = http.createServer(app).listen(8080,function(){
   console.log("익스프레스로 웹 서버를 실행함 : "+ "8080");
});
