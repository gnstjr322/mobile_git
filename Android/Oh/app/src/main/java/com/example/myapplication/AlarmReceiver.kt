package com.example.myapplication

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService
import java.util.ArrayList

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        var dbHelper2 : DBHelper = DBHelper(context,"SECURE.db",null,1)
        var Sett :String = dbHelper2.resultSett
        val fd = dbHelper2.getSecure() // 세팅 정보를 받는다.
        var fd1 = fd?.split('/') // id , pw 를 꺼내온다
        if(fd1 != null){
            Log.d("아이디,패스워드", " ${dbHelper2.getSecure()}")
<<<<<<< HEAD

            Http(context,"0").HttpAsyncTask(fd1[0],fd1[1]).execute("http:/13.124.174.165:6060/kau")
            Http(context,"1").HttpAsyncTask(fd1[0],fd1[1]).execute("http:/13.124.174.165:6060/kau")

=======
            Log.d("세팅", " ${dbHelper2.resultSett}")
            if(Sett[0]=='1'){ // 공지사항 노티
                Http(context,"0").HttpAsyncTask(fd1[0],fd1[1]).execute("http:/13.124.174.165:6060/kau")
            }
            if(Sett[1]=='1'){ // 시험시간표 노티
                Http(context,"1").HttpAsyncTask(fd1[0],fd1[1]).execute("http:/13.124.174.165:6060/kau")
            }
>>>>>>> 090a6d09b0780ef660e0e0fe430220eb1c8a0dec
        }
    }
}
