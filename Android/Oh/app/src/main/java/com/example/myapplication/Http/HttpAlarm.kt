package com.example.myapplication.Http

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
import com.example.myapplication.DB.DBHelper
import com.example.myapplication.DataType.Exam
import com.example.myapplication.DataType.Name
import com.example.myapplication.R
import com.example.myapplication.RegisterActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import java.io.IOException
import java.util.ArrayList
import java.util.concurrent.TimeUnit

//노티피케이션을 해주기 위해 통신을 하는 부분

class HttpAlarm(private val context: Context, private val num :String) {
    val dbHelper : DBHelper = DBHelper(context, "NAME.db", null, 2)

    @RequiresApi(Build.VERSION_CODES.CUPCAKE)
    inner class HttpAsyncTask(userID: String, userPass: String) : AsyncTask<String, Void, List<Name>>() { //첫번재

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
                .add("num", num)
                .build()


        override fun doInBackground(vararg params: String): List<Name>? {

            var noticeList: List<Name> = ArrayList()
            var examList: List<Exam> = ArrayList()
            var result:String = ""
            val strUrl = params[0]
            try {

                val request = Request.Builder()
                        .url(strUrl)
                        .post(formBody)
                        .build()

                // 응답
                val response = client.newCall(request).execute()
                val gson = Gson()
                val listType2 = object : TypeToken<ArrayList<Exam>>() {}.type
                val listType = object : TypeToken<ArrayList<Name>>() {}.type
                if (noticeList != null) {
                    if(num == "0"){
                        noticeList = gson.fromJson<List<Name>>(response.body!!.string(), listType)
                    }else if(num == "1"){
                        examList = gson.fromJson<List<Exam>>(response.body!!.string(), listType2)
                        for(i in examList){
                            result += i.date
                            result += i.mon
                            result += i.tue
                            result += i.wed
                            result += i.thu
                            result += i.fri
                        }
                    }

                }
            } catch (e: IOException) {
                e.printStackTrace()
            }

            if (noticeList != null) {
                if(num == "0"){
                    if(noticeList[0].day != dbHelper.selectDate()){
                        Log.d("통신", " ${noticeList}")
                        dbHelper.deleteName()
                        for(i in noticeList){
                            dbHelper.nameInsert(i.form,i.name,i.link,i.day)
                        }
                        sendNotification(context,"공지사항이 올라왔습니다")
                    }else{
                        //sendNotification(context,"공지사항에 변동사항이 없습니다.")
                    }
                }else if(num == "1"){
                    if(result != dbHelper.resultExam){
                        Log.d("통신", " ${examList}")
                        dbHelper.deleteCal()
                        for(i in examList){
                            dbHelper.calInsert(i.date,i.mon,i.tue,i.wed,i.thu,i.fri)
                        }
                        sendNotification(context,"시험시간표가 나왔습니다")
                    }else{
                        //sendNotification(context,"시험시간표에 변동사항이 없습니다.")
                    }
                }

            }
            return noticeList
        }


        @RequiresApi(Build.VERSION_CODES.CUPCAKE)
        override fun onPostExecute(nameList: List<Name>?) {
            super.onPostExecute(nameList)
            Toast.makeText(context, "세부내용을 불러 오는 중입니다.", Toast.LENGTH_SHORT).show()
            Log.d("asdasd", "start recieve")
            println("노티시작")
        }

        fun sendNotification(context: Context,msg:String) {
            val intent = Intent(context, RegisterActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            val pendingIntent = PendingIntent.getActivity(context, (System.currentTimeMillis()/1000).toInt()/* Request code */, intent, PendingIntent.FLAG_UPDATE_CURRENT)

            val channelId = "fcm_default_channel"//getString(R.string.default_notification_channel_id);
            val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            val notificationBuilder = NotificationCompat.Builder(context, channelId)
                    .setSmallIcon(R.drawable.appicon)//drawable.splash)
                    .setContentTitle("뭐 과제떴다고??")
                    .setContentText(msg)
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

            notificationManager.notify((System.currentTimeMillis()/1000).toInt() , notificationBuilder.build())

        }
    }

}