package com.example.myapplication.Fragment

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.myapplication.DataType.Exam
import com.example.myapplication.DataType.Name
import com.example.myapplication.DataType.Subject

class TabPagerAdapter(fm: FragmentManager, var noticeList : List<Name>, var examList : List<Exam>, var subjectList : List<Subject>, var userID : String, var userPass : String
                      , var Str_url : String?, private val tabcount: Int) : FragmentStatePagerAdapter(fm) {

    //화면에 프레그먼트를 띄우기 위해 어댑터를 이용함. 통신으로 받은 데이터들(register activity 에서)은 이 클래스를 통해 이동시킨다.
    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> {
                Log.d("Adapter", noticeList.toString())
                return TabFragment(noticeList)
            }
            1 -> {
                return TabFragment2(examList)
            }
            2 -> {
                return TabFragment3(subjectList, userID, userPass, Str_url)
            }
            3 -> {
                return TabFragment4()
            }
            else -> {
                return TabFragment(noticeList)
            }
        }
    }

    override fun getCount(): Int {

        return tabcount
    }
}
