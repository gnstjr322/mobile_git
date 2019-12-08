const puppeteer = require('puppeteer');
const cheerio = require('cheerio');

function delay( timeout ) { // 딜레이 함수
  return new Promise(( resolve ) => {
    setTimeout( resolve, timeout );
  });
}

function spliter(data){ // 문자열 파싱 작업 함수
  if(data.indexOf('감') != -1){
    var split1 = data.substring(0,data.indexOf('감'));
    var split2 = data.substring(data.indexOf('감'),);
    var split = split1 + '\n' + split2;
    split1 = split.substring(0,data.indexOf('장')+1);
    split2 = split.substring(data.indexOf('장')+1,);
    split = split1 + '\n' +split2;
    return split;
  }
  return data;
}
exports.crawlerj = function(id,pw,callback){ // 모듈화
  const url = 'https://www.kau.ac.kr/page/login.jsp?ppage=&target_page=act_Portal_Check.jsp@chk1-1';

  (async () => { // 동기를 맞추기 위해 어싱크를 사용
     function scrapdata(html) {
       const $ = cheerio.load(html);
       //const $2 = cheerio.load(html2);
       var data = new Array()
       $('body > form > table > tbody > tr:nth-child(3) > td > table > tbody > tr').each(function (index, ele) {
           var temp = Object()
           temp['date'] = $(this).find('td:nth-child(1)').text().trim()
           temp['mon'] = $(this).find('td:nth-child(2)').text().trim()
           temp['tue'] = $(this).find('td:nth-child(3)').text().trim()
           temp['wed'] = $(this).find('td:nth-child(4)').text().trim()
           temp['thu'] = $(this).find('td:nth-child(5)').text().trim()
           temp['fri'] = $(this).find('td:nth-child(6)').text().trim()
           temp['mon']=spliter(temp['mon']); // 파싱한 돔을 다시 파싱함
           temp['tue']=spliter(temp['tue']);
           temp['wed']=spliter(temp['wed']);
           temp['thu']=spliter(temp['thu']);
           temp['fri']=spliter(temp['fri']);
           data.push(temp)
       })
       console.log(data);
       return data;
     }
        const browserOption = {// 브라우저 옵션 설정
          headless: true // 디버깅용으로 false 지정하면 브라우저가 자동으로 열린다.
        };
        const browser = await puppeteer.launch(browserOption);//브라우저 생성
        const page = await browser.newPage(); // 페이지 생성
        let response;
        try {
          const pageOption = {// 탭 옵션
            waitUntil: 'networkidle2',// waitUntil: 적어도 500ms 동안 두 개 이상의 네트워크 연결이 없으면 탐색이 완료된 것으로 간주합니다.
            timeout: 20000, // 타임아웃 20초
          };
          response = await page.goto(url, pageOption);
          //await page.waitForNavigation();
          await page.waitForSelector('body');
          await page.type( "input[type=password]", pw ); // 아이디 입력
        	await page.type( "body > div.aside > div.articel > table:nth-child(2) > tbody > tr:nth-child(3) > td > form > table > tbody > tr:nth-child(3) > td > table > tbody > tr > td:nth-child(1) > table > tbody > tr:nth-child(1) > td:nth-child(2) > input", id ); // 패스워드 입력
          //await delay(100);
          //var html3 = await page.content();
          //console.log(html3);
          const elementHandle = await page.waitFor( "input" ); // 인풋
          await elementHandle.press( "Enter" );  // 입력
          await page.waitForSelector('html > frameset');
          await page.goto('https://portal.kau.ac.kr/sugang/SugangExamList.jsp');
          //await page.waitForNavigation();

        } catch (error) {
          await page.close();
          await browser.close();
          console.error(error);
          callback("error");
          return;
        }
        let html; // 첫번쨰 페이지
        //await console.log(html);
        try {
          html = await page.content();// 첫번째 페이지 크롤링
        } catch (error) {
          console.error(error);
          callback("error");
          return;
        } finally {
          await page.close();
          await browser.close();
        }
        const newsData = scrapdata(html);
        //console.log(newsData);
        callback(newsData);//콜백으로 결과값을 넘겨줌
  })();
}
