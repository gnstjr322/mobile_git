package com.example.myapplication

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.AsyncTask
import android.os.Build
import android.os.Build.ID
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.RemoteViews
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import java.io.IOException
import java.util.ArrayList
import java.util.concurrent.TimeUnit
import java.util.jar.Pack200.Packer.PASS

class TabFragment(var nameList : List<Name>) : Fragment() {


    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        var view: View = inflater.inflate(R.layout.fragment1, container, false)
        Log.d("최종정착지", nameList.toString())

        val mWeatherListView = view.findViewById<ListView>(R.id.list_view)
        val adapter = NameAdapter(nameList)
        //val adapter = ArrayAdapter(context, android.R.layout.simple_list_item_1, weatherList)
        mWeatherListView.adapter = adapter

        /* mWeatherListView.setOnItemClickListener{parent ,view , position, id->

             this.position = position

             val detailIntent = Intent(activity, DetailViewActivity::class.java)

             detailIntent.putStringArrayListExtra("Lists", weatherList)
             startActivityForResult(detailIntent,1)
         }*/

        /* if(weatherList != null) {
             val adapter = WeatherAdapter(weatherList)
             mWeatherListView!!.adapter = adapter
         }*/
        return view
    }

}
