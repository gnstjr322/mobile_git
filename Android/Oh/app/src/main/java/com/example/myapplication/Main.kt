package com.example.myapplication


import android.app.ProgressDialog
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
import java.lang.Exception
import java.util.ArrayList
import java.util.concurrent.TimeUnit


@RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
class Main : AppCompatActivity() {

    private var mWeatherListView: ListView? = null

    private var tabLayout: TabLayout? = null
    private var viewPager: ViewPager? = null
    var nameList2: List<Name> = ArrayList() // 넘겨줄걸 여기다 저장
    var nameList3: List<Cal> = ArrayList() // 넘겨줄걸 여기다 저장
    var nameList4: List<Subject> = ArrayList() // 넘겨줄걸 여기다 저장


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        mWeatherListView = findViewById(R.id.list_view) as? ListView


        nameList2 = intent.getParcelableArrayListExtra("nameList2")
        nameList3 = intent.getParcelableArrayListExtra("nameList3")
        nameList4 = intent.getParcelableArrayListExtra("nameList4")
        var userID = intent.getStringExtra("userID")
        var userPass = intent.getStringExtra("userPass")
        var Str_url  = intent.getStringExtra("url")

        Log.d("메인1", " $nameList2")
        Log.d("메인2", " $nameList3")
        Log.d("메인6", " $nameList4")
        Log.d("메인3", " $userID")
        Log.d("메인4", " $userPass")
        //var dbHelper : DBHelper = DBHelper(this,"NAME.db",null,1)
        var dbHelper1 : DBHelper = DBHelper(this,"SECURE.db",null,1)
        dbHelper1.secureInsert(userID,userPass)


        tabLayout = findViewById<View>(R.id.tabLayout) as TabLayout?
        tabLayout!!.tabGravity = TabLayout.GRAVITY_FILL
        //mWeatherListView = findViewById(R.id.list_view) as? ListView

        //탭페이지 어댑터 설정
        viewPager = findViewById<View>(R.id.viewpager) as? ViewPager


        val pagerAdapter = TabPagerAdapter(supportFragmentManager, nameList2, nameList3, nameList4, userID, userPass,Str_url,tabLayout!!.tabCount)
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
        if (nameList3 != null) {
            //Log.d("HttpAsyncTask", nameList.toString());
            val adapter = CalAdapter(nameList3)
            mWeatherListView?.adapter = adapter
        }
        if (nameList4 != null) {
            //Log.d("HttpAsyncTask", nameList.toString());
            val adapter = SubjectAdapter(nameList4)
            mWeatherListView?.adapter = adapter
        }
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

    }
*/


