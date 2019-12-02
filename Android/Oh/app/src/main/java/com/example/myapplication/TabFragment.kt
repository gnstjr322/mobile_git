package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.text.util.Linkify
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListView
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment1.*

class TabFragment(var nameList : List<Name>) : Fragment() {

    var position : Int? = null // 프로퍼티는 전역변수같은 의미라 생각해


    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        var view: View = inflater.inflate(R.layout.fragment1, container, false)
        Log.d("최종정착지", nameList.toString())

        val mWeatherListView = view.findViewById<ListView>(R.id.list_view)
        val adapter = NameAdapter(nameList)
        //val adapter = ArrayAdapter(context, android.R.layout.simple_list_item_1, weatherList)
        mWeatherListView.adapter = adapter

        mWeatherListView.setOnItemClickListener{parent ,view , position, id->

            this.position = position

            Log.d("aaadsd",nameList[position].toString())

            nameList[position].toString().split("'")

            var b = nameList[position].link
            //Linkify.addLinks(b, Linkify.WEB_URLS)
            //println(weatherList[position].country)
            val intent = Intent(activity, WebActivity::class.java)

            intent.putExtra("Link", b)

            startActivityForResult(intent,1)
        }
        return view
    }

}
