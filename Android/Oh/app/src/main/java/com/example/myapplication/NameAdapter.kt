package com.example.myapplication


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
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
            holder.form = convertView!!.findViewById(R.id.iv_text) as ImageView
            holder.name = convertView!!.findViewById(R.id.name_text) as TextView
            holder.link = convertView.findViewById(R.id.link_text) as TextView
            holder.day = convertView.findViewById(R.id.day_text) as TextView
            convertView.tag = holder
        } else {
            holder = convertView.tag as ViewHolder
        }

        val name1 = getItem(position) as Name
        if(name1.form == "gong")holder.form!!.setImageResource(R.drawable.gong)
        if(name1.form == "ilg")holder.form!!.setImageResource(R.drawable.ilg)
        holder.name!!.setText(name1.name)
        holder.link!!.setText(name1.link)
        holder.day!!.setText(name1.day)

        return convertView
    }

    // 뷰 홀더 패턴
    internal class ViewHolder {
        var form: ImageView? = null
        var name: TextView? = null
        var link: TextView? = null
        var day: TextView? = null
    }
}