package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.widget.SwitchCompat
import com.example.myapplication.DB.DBHelper


//알림설정을 해주는 액티비티
class Setting2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        var swb1 :SwitchCompat = findViewById(R.id.switch1)
        var swb2 :SwitchCompat = findViewById(R.id.switch2)
        var dbHelper1 : DBHelper = DBHelper(this, "SECURE.db", null, 1)
        swb1.setOnCheckedChangeListener { buttonView, isChecked ->
            println (isChecked)
            if(!isChecked){
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
            if(!isChecked){
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
