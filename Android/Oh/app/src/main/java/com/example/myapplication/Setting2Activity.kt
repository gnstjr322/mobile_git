package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_detail_view.*
import kotlinx.android.synthetic.main.login_activity.*
import android.text.util.Linkify
import android.widget.Switch
import androidx.appcompat.widget.SwitchCompat


//알림
class Setting2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        var swb1 :SwitchCompat = findViewById(R.id.switch1)
        var swb2 :SwitchCompat = findViewById(R.id.switch2)
        var dbHelper1 : DBHelper = DBHelper(this,"SECURE.db",null,1)
        swb1.setOnCheckedChangeListener { buttonView, isChecked ->
            println (isChecked)
            if(isChecked==false){
                dbHelper1.deleteSett()
                dbHelper1.settInsert(0,0) // 세팅 2개가 되어있는 상태
                Log.d("세팅", " ${dbHelper1.resultSett}")
            }else{
                dbHelper1.deleteSett()
                dbHelper1.settInsert(1,1) // 세팅 2개가 되어있는 상태
                Log.d("세팅", " ${dbHelper1.resultSett}")
            }
        }
        swb2.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked==false){
                dbHelper1.deleteSett()
                dbHelper1.settInsert(0,1) // 세팅 2개가 되어있는 상태
                Log.d("세팅", " ${dbHelper1.resultSett}")
            }else{
                dbHelper1.deleteSett()
                dbHelper1.settInsert(1,1) // 세팅 2개가 되어있는 상태
                Log.d("세팅", " ${dbHelper1.resultSett}")
            }
        }

    }

}
