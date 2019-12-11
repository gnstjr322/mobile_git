package com.example.myapplication.Fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.example.myapplication.Adapter.NameAdapter
import com.example.myapplication.Alarm.AlarmUtill
import com.example.myapplication.DataType.Name
import com.example.myapplication.R
import com.example.myapplication.WebActivity

class TabFragment(var noticeList : List<Name>) : Fragment() { //메인 화면 클래스(메인 엑티비티에서 여기로 먼저 넘어옴)

    var position : Int? = null // 프로퍼티는 전역변수같은 의미
    var lastTimeBackPressed : Long = 0

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        var view: View = inflater.inflate(R.layout.fragment1, container, false)
        Log.d("최종정착지", noticeList.toString())


        val listView = view.findViewById<ListView>(R.id.list_view)
        val adapter = NameAdapter(noticeList)

        adapter.notifyDataSetChanged()
        var context = getContext()
        if(context!=null){
            AlarmUtill(context).Alarm()
        }

        listView.adapter = adapter


        listView.setOnItemClickListener{ parent, view, position, id->

            this.position = position

            Log.d("aaadsd",noticeList[position].toString())

            noticeList[position].toString().split("'")

            var b = noticeList[position].link
            val intent = Intent(activity, WebActivity::class.java)

            intent.putExtra("Link", b)

            startActivityForResult(intent,1)
        }
        return view
    }


}
