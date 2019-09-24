package com.example.oh

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class Alarm : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm)
        val accounts = arrayListOf<String>("공지: 예술과 인간",
            "공지 : 프로그래밍 기초","공지 : IOT")
        val list : ListView = findViewById(R.id.list1)
        val ivset : ImageView = findViewById(R.id.ivSet)
        var intent = Intent(this,Detail_View::class.java)
        var intent2 = Intent(this,Setting::class.java)
        val adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,accounts )
        list.adapter = adapter
        ivset.setOnClickListener({
            startActivity(intent2)
        })
        list.setOnItemClickListener({parent, itemView,position,id->
            startActivity(intent)
        })
    }
}