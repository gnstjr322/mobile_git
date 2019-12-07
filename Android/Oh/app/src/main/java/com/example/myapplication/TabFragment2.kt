package com.example.myapplication

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.GridView
import androidx.fragment.app.Fragment

class TabFragment2(var nameList3 : List<Cal>) : Fragment() {
    private var mWeatherListView: GridView? = null


    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view: View = inflater.inflate(R.layout.fragment2, container, false)
        var context = context
        mWeatherListView = view.findViewById<GridView>(R.id.grid_view1)
        Log.d("최종정착지2", nameList3.toString())
        val adapter = CalAdapter(nameList3)
        adapter.notifyDataSetChanged()
        if(context!=null){
            AlarmUtill(context).Alarm()
        }
        if (mWeatherListView != null) {
            mWeatherListView!!.adapter = adapter
        }

        return view

    }

}
