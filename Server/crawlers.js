const puppeteer = require('puppeteer');
const cheerio = require('cheerio');

function delay( timeout ) {
  return new Promise(( resolve ) => {
    setTimeout( resolve, timeout );
  });
}
exports.crawlers = function(id,pw,callback){
  const url = 'https://lms.kau.ac.kr/login.php';
  let html; // 첫번쨰 페이지
  //await console.log(html);
  (async () => {
     function scrapdata(html) {
       const $ = cheerio.load(html);
       var data = new Array()
       $('#region-main > div > div.progress_courses > div.course_lists > ul > li').each(function (index, ele) {
           var temp = Object()
           temp['subject'] = $(this).find('div.course-title > h3').text().trim()
           temp['link'] = $(this).find('a').attr('href')
           if(temp['subject']!= "" || temp['link']!="")data.push(temp)
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
          await page.waitForSelector('div.textform >  input#input-username');
          await page.type( "div.textform >  input#input-username", id ); // 아이디 입력
        	await page.type( "div.textform >  input#input-password", pw ); // 패스워드 입력
          //await delay(100);
          //var html3 = await page.content();
          //console.log(html3);
          const elementHandle = await page.waitFor( "input" ); // 인풋
          await elementHandle.press( "Enter" );  // 입력
          await page.waitForSelector('#page-blocks > div.block.block-notification.block-coursemos > div.content.ubnotification > div.more_button > a');
          //await page.waitForNavigation();

        } catch (error) {
          await page.close();
          await browser.close();
          console.error(error);
          callback("error");
          return;
        } finally {

          html = await page.content();// 첫번째 페이지 크롤링
          await page.close();
          // 7. 브라우저 닫기
          await browser.close();
        }
        const newsData = scrapdata(html);
        //console.log(newsData);
        callback(newsData);//콜백으로 결과값을 넘겨줌
  })();
}
