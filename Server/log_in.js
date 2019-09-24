const puppeteer = require("puppeteer");


// 사용시 인위적인 딜레이를 주기위한 함수

function delay( timeout ) {

  return new Promise(( resolve ) => {

    setTimeout( resolve, timeout );

  });

}


puppeteer.launch({

	  headless : false	// 헤드리스모드의 사용여부를 묻는다.

	, devtools : true	// 개발자 모드의 사용여부를 묻는다.

}).then(async browser => {
	const page = await browser.newPage();
	// 로그인할 티스토리 블로그의 관리자 페이지를 지정

	await page.goto( "https://lms.kau.ac.kr/login.php", { waitUntil : "networkidle2" } );



	// 티스토리의 아이디와 암호를 입력한다.

	await page.type( "div.textform >  input#input-username", "2015125054" );

	await page.type( "div.textform >  input#input-password", "jooboo100@" );



	/* document.getElementByI로 직접 입력할 input BOX를 선택하여 작업하는 것도 가능하다.

	await page.evaluate(() => {

		document.getElementById( "div.box_login > div.inp_text:nth-child(1) > input#loginId" ).value = "티스토리 아이디";

                document.getElementById( "div.box_login > div.inp_text:nth-child(2) > input#loginPw" ).value = "티스토리 패스워드";

	});

	*/


	await delay(3000);



        // 로그인 SUBMIT 기능

	const elementHandle = await page.waitFor( "input" );

	await elementHandle.press( "Enter" );



	await delay(5000);



        /* 로그인이후 방문 기록 데이터를 콘솔에 띄워본다. */

	const emToDay = await page.waitFor( "div#page-blocks > div.block.block-upcomming.block-coursemos > div.content > ul.clearfix.timeline > li:nth-child(1) > a > div.title > h5");

        const txtToDay = await page.evaluate( emToDay => emToDay.textContent, emToDay );

        console.log("-. 1", txtToDay);




	const emYesterDay = await page.waitFor( "div#page-blocks > div.block.block-upcomming.block-coursemos > div.content > ul.clearfix.timeline > li:nth-child(2) > a > div.title > h5");

        const txtYesterDay = await page.evaluate( emYesterDay => emYesterDay.textContent, emYesterDay );

        console.log("-. 2", txtYesterDay);

/*
	const emCumulativ = await page.waitFor( "body.format-site course path-site safari dir-ltr lang-ko yui-skin-sam yui3-skin-sam lms-kau-ac-kr pagelayout-frontpage course-1 context-2 has-region-side-pre empty-region-side-pre has-region-side-post empty-region-side-post content-only layout-option-nonavbar jsenabled r1024 >div.page > div.pagemask > div.page-container > div.page-blocks > div.block block-upcomming block-coursemos > div.content > ul.clearfix timeline > li:nth-child(3) > h5");

        const txtCumulativ = await page.evaluate( emCumulativ => emCumulativ.textContent, emCumulativ );

        console.log("-. 3", txtCumulativ);

*/
});
