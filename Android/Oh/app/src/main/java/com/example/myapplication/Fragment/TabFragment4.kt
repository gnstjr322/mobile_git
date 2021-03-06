package com.example.myapplication.Fragment

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.myapplication.*
import com.example.myapplication.Alarm.AlarmUtill
import com.example.myapplication.DB.DBHelper

class TabFragment4 : Fragment() { //설정화면

    val List_MENU = arrayOf("내정보","알림","LMS","개발자","로그아웃")
    var position : Int? = null

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        var view: View = inflater.inflate(R.layout.fragment4, container, false)


        val listview = view.findViewById<ListView>(R.id.setting_list)
        val adapter = context?.let { ArrayAdapter(it,android.R.layout.simple_list_item_1,List_MENU) }

        if (adapter != null) {
            listview?.adapter = adapter
        }

        listview.setOnItemClickListener{parent ,view , position, id->

            this.position = position
            List_MENU[position].toString().split("'")

            var intent : Intent? = null

            if(position == 0) { // 내정보
                intent = Intent(activity, Setting3Activity::class.java) //내정보
            }
            else if(position == 1) { // 설정 항목 액티비티 띄우기
                intent = Intent(activity, Setting2Activity::class.java) //알림

            }

            else if(position == 2) { // LMS
                intent = Intent(activity, WebActivity::class.java) //알림
                intent.putExtra("Link","http://lms.kau.ac.kr/")
                startActivityForResult(intent,1)

            }
            else if(position == 4) { // 로그아웃
                var context = getContext()
                if(context != null){
                    val dbHelper2 : DBHelper = DBHelper(context, "SECURE.db", null, 1)
                    val dbHelper : DBHelper = DBHelper(context, "NAME.db", null, 2)
                    dbHelper.deleteAll()
                    dbHelper2.deleteSett()
                    val fd = dbHelper2.getSecure()
                    var fd1 = fd?.split('/')
                    if(fd1 != null){
                        dbHelper2.deleteSecure(fd1[0],fd1[1])
                    }
                    Log.d("지움1", " ${dbHelper2.getSecure()}")
                    Log.d("지움2", " ${dbHelper.getName} ${dbHelper.getSubject} ${dbHelper.getExam}")
                    intent = Intent(activity, RegisterActivity::class.java) // 로그아웃
                    startActivity(intent)
                    val auto : SharedPreferences = context.getSharedPreferences("auto", MODE_PRIVATE)
                    val editor = auto.edit()
                    editor.clear()
                    editor.commit()
                    AlarmUtill(context).cancelAlarm() // 알람 매니저를 끈다.
                    Toast.makeText(activity, "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show()
                }
            }
            else
                intent = Intent(activity, Setting1Activity::class.java) //개발자

            startActivityForResult(intent,1)
        }
        return view
    }


}