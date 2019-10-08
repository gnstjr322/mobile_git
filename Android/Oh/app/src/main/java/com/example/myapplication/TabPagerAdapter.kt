package com.example.myapplication

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import java.lang.NullPointerException

class TabPagerAdapter(fm: FragmentManager,var nameList2 : List<Name> ,private val tabcount: Int) : FragmentStatePagerAdapter(fm) {


    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> {
                Log.d("Adapter", nameList2.toString())
                return TabFragment(nameList2)
            }
            1 -> {
                return TabFragment2()
            }
            2 -> {
                return TabFragment3()
            }
            3 -> {
                return TabFragment4()
            }
            else -> {
                return TabFragment(nameList2)
            }
        }
    }

    override fun getCount(): Int {

        return tabcount
    }
}
