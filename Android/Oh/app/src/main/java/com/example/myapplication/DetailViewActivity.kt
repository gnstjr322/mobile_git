package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import android.widget.ArrayAdapter as ArrayAdapter1


class DetailViewActivity : AppCompatActivity() {
    // putExtra 한것을 가져와야 한다. 딕셔너리. account를 첨 사용할때 인덴트 한걸 따로 선언하지 않고 바로 사용할 수 있다
    //val wl by lazy { intent.extras["Lists"] as CheckingList}

    var linkName : ArrayList<String?>? = null
    var link : ArrayList<String?>? = null
    var position : Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_view2)

        linkName = intent?.getStringArrayListExtra("LinkName")
        link = intent?.getStringArrayListExtra("Link") //url

        Log.d("aaadsd", linkName.toString())
        Log.d("aaadsd2", link.toString())

        val list : ListView = findViewById(R.id.detail_list)

        val adapter = ArrayAdapter1(this, android.R.layout.simple_list_item_1, link as ArrayList<String>)

        list.adapter = adapter

        list.setOnItemClickListener{parent , view , position, id->       //마우스 클릭했을때 이벤트 나오는것... 람다 함수

            this.position = position
            val intent = Intent(this, WebActivity::class.java)


            intent.putExtra("Link",linkName!![position])


            startActivityForResult(intent, 1)
        }



    }


}
