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
