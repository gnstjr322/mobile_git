package com.example.myapplication


import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*


import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager

import com.google.android.material.tabs.TabLayout
import java.lang.System.exit
import java.util.ArrayList


@RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
class Main : AppCompatActivity() {

    private var mWeatherListView: ListView? = null

    private var tabLayout: TabLayout? = null
    private var viewPager: ViewPager? = null
    var nameList2: List<Name> = ArrayList() // 넘겨줄걸 여기다 저장
    var nameList3: List<Cal> = ArrayList() // 넘겨줄걸 여기다 저장
    var nameList4: List<Subject> = ArrayList() // 넘겨줄걸 여기다 저장
    var lastTimeBackPressed : Long = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mWeatherListView = findViewById(R.id.list_view) as? ListView
        val dbHelper : DBHelper = DBHelper(this,"NAME.db",null,2)
        nameList2 = dbHelper.getName
        nameList4 = dbHelper.getSubject
        nameList3 = dbHelper.getCal

        var userID = intent.getStringExtra("userID")
        var userPass = intent.getStringExtra("userPass")
        var Str_url  = intent.getStringExtra("url")
        var dbHelper1 : DBHelper = DBHelper(this,"SECURE.db",null,1)
        dbHelper1.settInsert(1,1) // 세팅 2개가 되어있는 상태
        dbHelper1.secureInsert(userID,userPass)


        tabLayout = findViewById<View>(R.id.tabLayout) as TabLayout?
        tabLayout!!.tabGravity = TabLayout.GRAVITY_FILL

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
        })
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

    override  fun onBackPressed() {

        if (System.currentTimeMillis() - lastTimeBackPressed < 1500) {
            finishAffinity()
            return
        }
        lastTimeBackPressed = System.currentTimeMillis()
        Toast.makeText(this, "'뒤로' 버튼을 한 번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show()
    }

}
