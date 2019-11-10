package com.example.myapplication

import android.content.ContentValues.TAG
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.GridView
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.fragment1.*
import kotlinx.android.synthetic.main.fragment2.*
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import java.io.IOException
import java.util.ArrayList
import java.util.concurrent.TimeUnit

class TabFragment2(var nameList3 : List<Cal>) : Fragment() {
    private var mWeatherListView: GridView? = null


    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view: View = inflater.inflate(R.layout.fragment2, container, false)

        mWeatherListView = view.findViewById<GridView>(R.id.grid_view1)
        Log.d("최종정착지2", nameList3.toString())


        val adapter = CalAdapter(nameList3)
        if (mWeatherListView != null) {
            mWeatherListView!!.adapter = adapter
        }

        return view

    }

}
