package com.example.myapplication.Adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.myapplication.DataType.Exam
import com.example.myapplication.R

class ExamAdapter(var cList: List<Exam>) : BaseAdapter() { //시험 시간표를 위한 어댑터(그리드뷰와 파싱받아온 리스트를 연결)


    override fun getCount(): Int {
        return cList.size
    }

    override fun getItem(position: Int): Any {
        return cList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val holder: ViewHolder
        if (convertView == null) {
            convertView =
                LayoutInflater.from(parent.context).inflate(R.layout.item_calendar, parent, false)

            holder = ViewHolder()
            holder?.date = convertView!!.findViewById(R.id.date_text) as TextView
            holder?.mon = convertView.findViewById(R.id.mon_text) as TextView
            holder?.tue = convertView.findViewById(R.id.tue_text) as TextView
            holder?.wed = convertView.findViewById(R.id.wed_text) as TextView
            holder?.thu = convertView.findViewById(R.id.thu_text) as TextView
            holder?.fri = convertView.findViewById(R.id.fri_text) as TextView
            convertView.tag = holder
        } else {
            holder = convertView.tag as ViewHolder
        }

        val name1 = getItem(position) as Exam?
        holder.date?.setText(name1?.date)
        holder.mon?.setText(name1?.mon)
        holder.tue?.setText(name1?.tue)
        holder.wed?.setText(name1?.wed)
        holder.thu?.setText(name1?.thu)
        holder.fri?.setText(name1?.fri)

        return convertView
    }

    // 뷰 홀더 패턴
    internal class ViewHolder {
        var date: TextView? = null
        var mon: TextView? = null
        var tue: TextView? = null
        var wed: TextView? = null
        var thu: TextView? = null
        var fri: TextView? = null
    }
}