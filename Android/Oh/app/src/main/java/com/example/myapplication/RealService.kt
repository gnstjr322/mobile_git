package com.example.myapplication

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.RingtoneManager
import android.net.Uri
import android.os.AsyncTask
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.util.Log
import android.widget.RemoteViews
import android.widget.Toast
import androidx.annotation.RequiresApi

import androidx.core.app.NotificationCompat
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import java.io.IOException
import java.lang.Exception

import java.text.SimpleDateFormat
import java.util.ArrayList
import java.util.Calendar
import java.util.Date
import java.util.concurrent.TimeUnit

class RealService : Service() {
    private var mainThread: Thread? = null
    private var nameList2 :  List<Name> = ArrayList()
    //val nameList2 by lazy { intent.getParcelableArrayListExtra["Account"] as Name}
    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        serviceIntent = intent

        mainThread = Thread(Runnable {
            val sdf = SimpleDateFormat("aa hh:mm")
            var run = true
            while (run) {
                try {
                    //Thread.sleep((1000 * 60 * 1).toLong()) // 1 minute
                    Thread.sleep((1000 * 30 * 1).toLong()) // 30초
                    val date = Date()
                    //showToast(getApplication(), sdf.format(date));
                    HttpAsyncTask("2015125054", "jooboo100@",sdf.format(date)).execute("http:/172.30.1.13:8080")
                    //sendNotification(sdf.format(date))
                } catch (e: InterruptedException) {
                    run = false
                    e.printStackTrace()
                }

            }
        })
        mainThread!!.start()

        return Service.START_NOT_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()

        serviceIntent = null
        setAlarmTimer()
        Thread.currentThread().interrupt()

        if (mainThread != null) {
            mainThread!!.interrupt()
            mainThread = null
        }
    }

    override fun onCreate() {
        super.onCreate()
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onUnbind(intent: Intent): Boolean {
        return super.onUnbind(intent)
    }


    protected fun setAlarmTimer() {
        val c = Calendar.getInstance()
        c.timeInMillis = System.currentTimeMillis()
        c.add(Calendar.SECOND, 1)
        val intent = Intent(this, AlarmRecever::class.java)
        val sender = PendingIntent.getBroadcast(this, 0, intent, 0)

        val mAlarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        mAlarmManager.set(AlarmManager.RTC_WAKEUP, c.timeInMillis, sender)
    }

    fun sendNotification(messageBody: String) {
        val intent = Intent(this, RegisterActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent, PendingIntent.FLAG_ONE_SHOT)

        val channelId = "fcm_default_channel"//getString(R.string.default_notification_channel_id);
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        ////////////

        ////////////

        val notificationBuilder = NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.ic_launcher_background)//drawable.splash)
                .setContentTitle("새로운 공지사항이 올라왔습니다.")
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setColor(Color.GREEN)
                .setSound(defaultSoundUri)
                .setPriority(Notification.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        /////////////////////////

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, "Channel human readable title", NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build())

    }

    fun sendNotification2(messageBody: String) {
        val intent = Intent(this, RegisterActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent, PendingIntent.FLAG_ONE_SHOT)

        val channelId = "fcm_default_channel"//getString(R.string.default_notification_channel_id);
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        ////////////

        ////////////

        val notificationBuilder = NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.ic_launcher_background)//drawable.splash)
                .setContentTitle("바뀐공지사항이 없습니다.")
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setColor(Color.GREEN)
                .setSound(defaultSoundUri)
                .setPriority(Notification.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        /////////////////////////

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, "Channel human readable title", NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build())

    }

    companion object {
        var serviceIntent: Intent? = null
    }
    @RequiresApi(Build.VERSION_CODES.CUPCAKE)
    private inner class HttpAsyncTask(var userID: String, var userPass: String, var sdf:String) : AsyncTask<String, Void, List<Name>>() { //첫번재

        override fun onPreExecute() {
            super.onPreExecute()

        }


        // OkHttp 클라이언트
        internal var client = OkHttpClient.Builder()
                .connectTimeout(600, TimeUnit.SECONDS)
                .writeTimeout(600, TimeUnit.SECONDS)
                .readTimeout(600, TimeUnit.SECONDS)
                .build()

        internal var formBody: RequestBody = FormBody.Builder()
                .add("id", userID)
                .add("pw", userPass)
                .add("num", "0")
                .build()


        override fun doInBackground(vararg params: String): List<Name>? {

            var nameList: List<Name> = ArrayList()
            val strUrl = params[0]
            try {
                //notify2()
                //Response response2 = client.newCall(request2).execute();
                // 요청

                val request = Request.Builder()
                        .url(strUrl)
                        .post(formBody)
                        .build()

                // 응답
                val response = client.newCall(request).execute()
                val gson = Gson()
                val listType = object : TypeToken<ArrayList<Name>>() {

                }.type
                nameList = gson.fromJson<List<Name>>(response.body!!.string(), listType)
                //Log.d(TAG, "onCreate: " + weatherList.toString());
                // notify2()
            } catch (e: IOException) {
                //Toast.makeText(applicationContext, "로그인에 실패하였습니다.", Toast.LENGTH_SHORT).show()
                e.printStackTrace()
            }
            nameList2 = nameList
            return nameList
        }


        @RequiresApi(Build.VERSION_CODES.CUPCAKE)
        override fun onPostExecute(nameList: List<Name>?) {
            super.onPostExecute(nameList)
            var dbHelper : DBHelper = DBHelper(this@RealService,"NAME.db",null,1)
            var fd1 :String? = dbHelper.selectDate()
            if(nameList2[0].day!=fd1){
                sendNotification(sdf)
            }else{
                sendNotification2(sdf)
            }

        }
    }

}
