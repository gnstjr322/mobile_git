package com.example.myapplication


import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.*


import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.myapplication.Adapter.ExamAdapter
import com.example.myapplication.Adapter.SubjectAdapter
import com.example.myapplication.Fragment.TabPagerAdapter
import com.example.myapplication.DB.DBHelper
import com.example.myapplication.DataType.Exam
import com.example.myapplication.DataType.Name
import com.example.myapplication.DataType.Subject

import com.google.android.material.tabs.TabLayout
import java.util.ArrayList


@RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
class MainActivity : AppCompatActivity() {

    private var mainListView: ListView? = null

    private var tabLayout: TabLayout? = null
    private var viewPager: ViewPager? = null
    var noticeList: List<Name> = ArrayList() // 넘겨줄걸 여기다 저장
    var examList: List<Exam> = ArrayList() // 넘겨줄걸 여기다 저장
    var subjectList: List<Subject> = ArrayList() // 넘겨줄걸 여기다 저장
    var lastTimeBackPressed : Long = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainListView = findViewById(R.id.list_view) as? ListView
        val dbHelper : DBHelper = DBHelper(this, "NAME.db", null, 2)
        var dbHelper1 : DBHelper = DBHelper(this, "SECURE.db", null, 1)
        var userID = intent.getStringExtra("userID")
        var userPass = intent.getStringExtra("userPass")
        var Str_url  = intent.getStringExtra("url")


        noticeList = dbHelper.getName
        subjectList = dbHelper.getSubject
        examList = dbHelper.getExam
        //dbHelper1.settInsert(1,1) // 세팅 2개가 되어있는 상태
        //dbHelper1.secureInsert(userID,userPass)

        //탭레이아웃의 탭Gravity는 탭의 정렬방식옵션
        tabLayout = findViewById<View>(R.id.tabLayout) as TabLayout?
        //Gravity_fil은 탭의 너비를 동일하게정렬
        tabLayout!!.tabGravity = TabLayout.GRAVITY_FILL

        //탭페이지 어댑터 설정
        viewPager = findViewById<View>(R.id.viewpager) as? ViewPager
        val pagerAdapter = TabPagerAdapter(supportFragmentManager, noticeList, examList, subjectList, userID, userPass, Str_url, tabLayout!!.tabCount)
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
        if (examList != null) {
            //Log.d("HttpAsyncTask", nameList.toString());
            val adapter = ExamAdapter(examList)
            mainListView?.adapter = adapter
        }
        if (subjectList != null) {
            //Log.d("HttpAsyncTask", nameList.toString());
            val adapter = SubjectAdapter(subjectList)
            mainListView?.adapter = adapter
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
