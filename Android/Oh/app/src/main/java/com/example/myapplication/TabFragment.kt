package com.example.myapplication

import android.content.Intent
import android.os.Bundle
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

            //println(weatherList[position].toString().split("'"))
            nameList[position].toString().split("'")

            //var accounts = arrayOf(CheckingList(weatherList[position].country, weatherList[position].weather, weatherList[position].temperature))

            var a = nameList[position].name
            var b = nameList[position].link
            var c: String? = nameList[position].day

            //println(weatherList[position].country)
            val intent = Intent(activity, DetailViewActivity::class.java)

            intent.putExtra("Name", a)
            intent.putExtra("Link", b)
            intent.putExtra("Day", c)

            startActivityForResult(intent,1)
        }
        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(data !=null) {
            //val accountA = data.getStringExtra("ListsBackA")
            val accountB = data.getStringExtra("ListsBackB")
            //val accountC = data.getStringExtra("ListsBackC")
            if (position != null) {
                if (accountB!= null) {
                    nameList[position!!].link = accountB
                }
                (list_view.adapter as BaseAdapter).notifyDataSetChanged()

            }
        }

    }
}
