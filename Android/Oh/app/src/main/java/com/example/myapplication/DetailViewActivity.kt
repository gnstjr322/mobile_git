package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_detail_view.*
import kotlinx.android.synthetic.main.login_activity.*
import android.text.util.Linkify



class DetailViewActivity : AppCompatActivity() {
    // putExtra 한것을 가져와야 한다. 딕셔너리. account를 첨 사용할때 인덴트 한걸 따로 선언하지 않고 바로 사용할 수 있다
    //val wl by lazy { intent.extras["Lists"] as CheckingList}

    var b1 : String? = null

    // 돈계산된거 보이게 한다. 텍스트로
    fun  reload(){
        textView22.text = b1// text는 문자열 형식이기 떄문에 toString 해줘야함
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_view)

        b1 = intent.getStringExtra("Link")

        reload()
        Linkify.addLinks(textView22, Linkify.WEB_URLS)

    }

    override fun onBackPressed() { // back버튼 누르면 바로 반응하게한다.( 메인 액티비티로

        val resultIntent = Intent(this, TabFragment::class.java)

        resultIntent.putExtra("ListsBackB", b1)


        setResult(1,resultIntent)
        super.onBackPressed()
    }


}