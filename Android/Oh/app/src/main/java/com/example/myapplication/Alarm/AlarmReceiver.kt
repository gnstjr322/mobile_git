package com.example.myapplication.Alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.myapplication.DB.DBHelper
import com.example.myapplication.Http.HttpAlarm

class AlarmReceiver : BroadcastReceiver() { // 통신을 주기적으로 하기 위해 알람 리시버를 활용함.(db에 있는 아이디 비번으로 주기적으로 통신을 해줌-> 이부분은 HttpALarm 클래스 이용)
    override fun onReceive(context: Context, intent: Intent) {
        var dbHelper2 : DBHelper = DBHelper(context, "SECURE.db", null, 1)
        var Sett :String = dbHelper2.resultSett
        val fd = dbHelper2.getSecure() // 세팅 정보를 받는다.
        var fd1 = fd?.split('/') // id , pw 를 꺼내온다
        if(fd1 != null){
            Log.d("아이디,패스워드", " ${dbHelper2.getSecure()}")

            Log.d("세팅", " ${dbHelper2.resultSett}")
            if(Sett[0]=='1'){ // 공지사항 노티
                HttpAlarm(context, "0").HttpAsyncTask(fd1[0],fd1[1]).execute("http:/13.124.174.165:6060/kau")
            }
            if(Sett[1]=='1'){ // 시험시간표 노티
                HttpAlarm(context, "1").HttpAsyncTask(fd1[0],fd1[1]).execute("http:/13.124.174.165:6060/kau")
            }
        }
    }
}
