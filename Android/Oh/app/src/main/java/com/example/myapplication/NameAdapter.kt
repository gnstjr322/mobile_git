package com.example.myapplication


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class NameAdapter(var mList: List<Name>) : BaseAdapter() {
   // private val mList: List<Name>

    /*constructor(list: List<Name>) {
        mList = list
    }*/

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
                LayoutInflater.from(parent.context).inflate(R.layout.item_weather, parent, false)

            holder = ViewHolder()
            holder.name = convertView!!.findViewById(R.id.name_text) as TextView
            holder.link = convertView.findViewById(R.id.link_text) as TextView
            holder.day = convertView.findViewById(R.id.day_text) as TextView
            convertView.tag = holder
        } else {
            holder = convertView.tag as ViewHolder
        }

        val weather = getItem(position) as Name
        holder.name!!.setText(weather.name)
        holder.link!!.setText(weather.link)
        holder.day!!.setText(weather.day)

        return convertView
    }

    // 뷰 홀더 패턴
    internal class ViewHolder {
        var name: TextView? = null
        var link: TextView? = null
        var day: TextView? = null
    }
}