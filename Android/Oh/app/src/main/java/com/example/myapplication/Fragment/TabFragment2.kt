package com.example.myapplication.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import androidx.fragment.app.Fragment
import com.example.myapplication.Adapter.ExamAdapter
import com.example.myapplication.Alarm.AlarmUtill
import com.example.myapplication.DataType.Exam
import com.example.myapplication.R

class TabFragment2(var examList : List<Exam>) : Fragment() { //시험시간표 화면
    private lateinit var gridView: GridView


    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view: View = inflater.inflate(R.layout.fragment2, container, false)
        var context = context
        gridView = view.findViewById<GridView>(R.id.grid_view1)
        Log.d("최종정착지2", examList.toString())
        val adapter = ExamAdapter(examList)
        adapter.notifyDataSetChanged()
        if(context!=null){
            AlarmUtill(context).Alarm()
        }
        if (gridView != null) {
            gridView.adapter = adapter
        }

        return view

    }

}
