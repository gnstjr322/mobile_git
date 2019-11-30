package com.example.myapplication

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.RingtoneManager
import android.os.AsyncTask
import android.os.Build
import android.util.Log
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
import java.util.ArrayList
import java.util.concurrent.TimeUnit
//이거 실험용으로 통신파트 빼봄

class Http(private val context: Context) {
    var nameList2:List<Name> = ArrayList()


    @RequiresApi(Build.VERSION_CODES.CUPCAKE)
    inner class HttpAsyncTask(var userID: String, var userPass: String) : AsyncTask<String, Void, List<Name>>() { //첫번재

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

                val request = Request.Builder()
                        .url(strUrl)
                        .post(formBody)
                        .build()

                // 응답
                val response = client.newCall(request).execute()
                val gson = Gson()
                val listType = object : TypeToken<ArrayList<Name>>() {

                }.type
                if (nameList != null) {
                    nameList = gson.fromJson<List<Name>>(response.body!!.string(), listType)
                }
                //Log.d(TAG, "onCreate: " + weatherList.toString());
                // notify2()
            } catch (e: IOException) {
                //Toast.makeText(applicationContext, "로그인에 실패하였습니다.", Toast.LENGTH_SHORT).show()
                e.printStackTrace()
            }
            if (nameList != null) {
                nameList2 = nameList
                Log.d("통신", " ${nameList2}")
            }
            return nameList
        }


        @RequiresApi(Build.VERSION_CODES.CUPCAKE)
        override fun onPostExecute(nameList: List<Name>?) {
            super.onPostExecute(nameList)
            Log.d("내용", " ${nameList}")
            Toast.makeText(context, "방송받음", Toast.LENGTH_SHORT).show()
            println("FUCK")
            Log.d("asdasd", "start recieve")
            sendNotification(context)
            println("노티시작")
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

}