package com.example.myapplication


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class SearchAdapter(var mList: List<WeekDetail>) : BaseAdapter() {

    override fun getCount(): Int {
        return mList.size
    }

    override fun getItem(position: Int): Any {
        return mList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val holder: ViewHolder
        if (convertView == null) {
            convertView =
                LayoutInflater.from(parent.context).inflate(R.layout.item_weather2, parent, false)

            holder = ViewHolder()
            holder.title = convertView!!.findViewById(R.id.text_text) as TextView
            holder.data = convertView.findViewById(R.id.data_text) as TextView
            convertView.tag = holder
        } else {
            holder = convertView.tag as ViewHolder
        }

        val name1 = getItem(position) as WeekDetail
        holder.title!!.setText(name1.title)
        holder.data!!.setText(name1.data)


        return convertView
    }

    // 뷰 홀더 패턴
    internal class ViewHolder {
        var title: TextView? = null
        var data: TextView? = null

    }
}