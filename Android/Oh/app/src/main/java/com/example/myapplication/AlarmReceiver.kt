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
        var nameList:List<Name> = ArrayList()
        val fd = dbHelper2.getSecure()
        var fd1 = fd?.split('/') // id , pw 를 꺼내온다
        if(fd1 != null){
            Log.d("아이디,패스워드", " ${dbHelper2.getSecure()}")

            Http(context,"0").HttpAsyncTask(fd1[0],fd1[1]).execute("http:/13.124.174.165:6060/kau")
            Http(context,"1").HttpAsyncTask(fd1[0],fd1[1]).execute("http:/13.124.174.165:6060/kau")

        }
        /*
        Log.d("내용", " ${nameList}")
        Toast.makeText(context, "방송받음", Toast.LENGTH_SHORT).show()
        println("FUCK")
        Log.d("asdasd", "start recieve")
        sendNotification(context)
        println("노티시작")
         */
    }

    fun sendNotification(context: Context) {
        val intent = Intent(context, RegisterActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(context, 0 /* Request code */, intent, PendingIntent.FLAG_ONE_SHOT)

        val channelId = "fcm_default_channel"//getString(R.string.default_notification_channel_id);
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val notificationBuilder = NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.ic_launcher_background)//drawable.splash)
                .setContentTitle("알람매니저입니다.")
                .setContentText("공지사항을 확인해주세요")
                .setAutoCancel(true)
                .setColor(Color.GREEN)
                .setSound(defaultSoundUri)
                .setPriority(Notification.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, "Channel human readable title", NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(0 , notificationBuilder.build())

    }

}
