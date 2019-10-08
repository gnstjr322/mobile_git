package com.example.myapplication


import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*


import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager

import com.google.android.material.tabs.TabLayout
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import java.io.IOException
import java.util.ArrayList
import java.util.concurrent.TimeUnit


@RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
class Main : AppCompatActivity() {

    private var mWeatherListView: ListView? = null

    private var tabLayout: TabLayout? = null
    private var viewPager: ViewPager? = null
    var nameList2: List<Name> = ArrayList() // 넘겨줄걸 여기다 저장


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mWeatherListView = findViewById(R.id.list_view) as? ListView


        HttpAsyncTask().execute("http://192.168.168.178:8080")

    }

    private inner class HttpAsyncTask : AsyncTask<String, Void, List<Name>>() {
        //private val TAG = HttpAsyncTask::class.java.simpleName
        //val intent = intent
        var userID = intent.getStringExtra("userID")
        var userPass = intent.getStringExtra("userPass")


        // OkHttp 클라이언트
        internal var client = OkHttpClient.Builder()
                .connectTimeout(600, TimeUnit.SECONDS)
                .writeTimeout(600, TimeUnit.SECONDS)
                .readTimeout(600, TimeUnit.SECONDS)
                .build()

        internal var formBody: RequestBody = FormBody.Builder()
                .add("id", userID)
                .add("pw", userPass)
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
                e.printStackTrace()
            }

            //Log.d(TAG, nameList.toString())//nameList의 형식은 List<Name>

            nameList2 = nameList
            return nameList
        }


        override fun onPostExecute(nameList: List<Name>?) {
            super.onPostExecute(nameList)

            tabLayout = findViewById<View>(R.id.tabLayout) as TabLayout?
            tabLayout!!.tabGravity = TabLayout.GRAVITY_FILL
            //mWeatherListView = findViewById(R.id.list_view) as? ListView

            //탭페이지 어댑터 설정
            viewPager = findViewById<View>(R.id.viewpager) as? ViewPager

            Log.d("main", " $nameList2")
            val pagerAdapter = TabPagerAdapter(supportFragmentManager, nameList2,tabLayout!!.tabCount)
            viewPager!!.adapter = pagerAdapter

            //페이지 체인지 리스너 설정
            viewPager!!.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
            tabLayout!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab) {
                    viewPager!!.currentItem = tab.position
                }

                override fun onTabUnselected(tab: TabLayout.Tab) {

                }

                override fun onTabReselected(tab: TabLayout.Tab) {

                }
            }

            )
        }
    }


/*
    fun notify2(){
        lateinit var notificationManager: NotificationManager
        lateinit var notificationChannel: NotificationChannel
        lateinit var builder: Notification.Builder
        val channelId = "com.example.noti"
        val description = "뭐?과제떴다고?"

        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val intent = Intent(this, LauncherActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        val contentView = RemoteViews(packageName, R.layout.notification_layout)

        contentView.setTextViewText(R.id.tv_title, "새로운 공지사항이 추가 되었습니다.")
        contentView.setTextViewText(R.id.tv_content, "모바일 sw")

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            notificationChannel = NotificationChannel(channelId, description, NotificationManager.IMPORTANCE_HIGH)
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.GREEN
            notificationChannel.enableVibration(false)
            notificationManager.createNotificationChannel(notificationChannel)

            builder = Notification.Builder(this, channelId)
                    .setContent(contentView)
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setLargeIcon(BitmapFactory.decodeResource(this.resources, R.drawable.ic_launcher_background))
                    .setContentIntent(pendingIntent)

        } else {
            builder = Notification.Builder(this)
                    .setContent(contentView)
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setLargeIcon(BitmapFactory.decodeResource(this.resources, R.drawable.ic_launcher_background))
                    .setContentIntent(pendingIntent)

        }

        notificationManager.notify(1234, builder.build())

    }*/

}
