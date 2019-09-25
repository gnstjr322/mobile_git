package com.example.oh

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_alarm.*

class Alarm : AppCompatActivity() {

    lateinit var notificationManager: NotificationManager
    lateinit var notificationChannel: NotificationChannel
    lateinit var builder : Notification.Builder
    private val channelId = "package com.example.notifi"
    private val description = "Test notification"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm)
        val accounts = arrayListOf<String>("공지: 예술과 인간",
            "공지 : 프로그래밍 기초","공지 : IOT")
        val list : ListView = findViewById(R.id.list1)
        val ivset : Button = findViewById(R.id.button_setting)
        var intent = Intent(this,Detail_View::class.java)
        var intent2 = Intent(this,Setting::class.java)
        val adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,accounts )
        list.adapter = adapter

        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        button_alarm.setOnClickListener{
            val intent3 = Intent(this, LauncherActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(this,0,intent3, PendingIntent.FLAG_UPDATE_CURRENT)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                notificationChannel =
                    NotificationChannel(channelId, description, NotificationManager.IMPORTANCE_HIGH)
                notificationChannel.enableLights((true))
                notificationChannel.lightColor = Color.GREEN
                notificationChannel.enableVibration(false)
                notificationManager.createNotificationChannel(notificationChannel)

                builder = Notification.Builder(this, channelId)
                    .setContentTitle("뭐?과제떴다고?!")
                    .setContentText("Test Notification")
                    .setSmallIcon(R.mipmap.ic_launcher_round)
                    .setLargeIcon(BitmapFactory.decodeResource(this.resources,R.mipmap.ic_launcher))
                    .setContentIntent(pendingIntent)

            }else{
                builder = Notification.Builder(this)
                    .setContentTitle("뭐?과제떴다고?!")
                    .setContentText("Test Notification")
                    .setSmallIcon(R.mipmap.ic_launcher_round)
                    .setLargeIcon(BitmapFactory.decodeResource(this.resources,R.mipmap.ic_launcher))
                    .setContentIntent(pendingIntent)
            }
            notificationManager.notify(1234,builder.build())
        }

        ivset.setOnClickListener({
            startActivity(intent2)
        })
        list.setOnItemClickListener({parent, itemView,position,id->
            startActivity(intent)
        })
    }
}