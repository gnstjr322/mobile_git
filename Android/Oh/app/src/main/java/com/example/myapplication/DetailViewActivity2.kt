package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_detail_view.*
import kotlinx.android.synthetic.main.login_activity.*
import android.text.util.Linkify
import android.webkit.WebView
import android.widget.ListView
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_detail_view2.*
import android.widget.ArrayAdapter as ArrayAdapter1


class DetailViewActivity2 : AppCompatActivity() {
    // putExtra 한것을 가져와야 한다. 딕셔너리. account를 첨 사용할때 인덴트 한걸 따로 선언하지 않고 바로 사용할 수 있다
    //val wl by lazy { intent.extras["Lists"] as CheckingList}

    var b1 : ArrayList<String?>? = null
    var b2 : ArrayList<String?>? = null
    var position : Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_view2)

        b1 = intent?.getStringArrayListExtra("LinkName")
        b2 = intent?.getStringArrayListExtra("Link") //url

        Log.d("aaadsd", b1.toString())
        Log.d("aaadsd2", b2.toString())

        val list : ListView = findViewById(R.id.detail_list)

        val adapter = ArrayAdapter1(this, android.R.layout.simple_list_item_1, b2 as ArrayList<String>)

        list.adapter = adapter

        list.setOnItemClickListener{parent , view , position, id->       //마우스 클릭했을때 이벤트 나오는것... 람다 함수

            this.position = position
            val intent = Intent(this, WebActivity::class.java)


            intent.putExtra("Link",b1!![position])


            startActivityForResult(intent, 1)
        }



    }

    override fun onBackPressed() { // back버튼 누르면 바로 반응하게한다.( 메인 액티비티로

        val resultIntent = Intent(this, TabFragment3::class.java)

        resultIntent.putExtra("ListsBackB", b1)


        setResult(1,resultIntent)
        super.onBackPressed()
    }
}
