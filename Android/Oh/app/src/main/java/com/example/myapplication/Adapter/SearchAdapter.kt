package com.example.myapplication.Adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.myapplication.R
import com.example.myapplication.DataType.WeekDetail

class SearchAdapter(var mList: List<WeekDetail>) : BaseAdapter() { //디테일 액티비티에서 리스트뷰를 보여주기 위한 어댑터( 주차별 상세 내용)

    override fun getCount(): Int {
        return mList.size
    }

    override fun getItem(position: Int): Any {
        return mList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
        var convertView = convertView
        val holder: ViewHolder
        if (convertView == null) {
            convertView =
                LayoutInflater.from(parent.context).inflate(R.layout.item_weather2, parent, false)

            holder = ViewHolder()
            holder.title = convertView.findViewById(R.id.text_text) as TextView
            holder.data = convertView.findViewById(R.id.data_text) as TextView
            convertView.tag = holder
        } else {
            holder = convertView.tag as ViewHolder
        }

        val name1 = getItem(position) as WeekDetail
        holder.title?.setText(name1.title)
        holder.data?.setText(name1.data)


        return convertView
    }

    // 뷰 홀더 패턴
    internal class ViewHolder {
        var title: TextView? = null
        var data: TextView? = null

    }
}