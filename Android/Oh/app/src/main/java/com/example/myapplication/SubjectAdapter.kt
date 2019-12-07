package com.example.myapplication


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class SubjectAdapter(var sList: List<Subject>?) : BaseAdapter() {


    override fun getCount(): Int {
        return sList!!.size
    }

    override fun getItem(position: Int): Any {
        return sList!![position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val holder: ViewHolder
        if (convertView == null) {
            convertView =
                    LayoutInflater.from(parent.context).inflate(R.layout.item_subject, parent, false)

            holder = ViewHolder()
            holder.subject = convertView!!.findViewById(R.id.subject_text) as TextView
            //holder.link = convertView.findViewById(R.id.link_text) as TextView
            convertView.tag = holder
        } else {
            holder = convertView.tag as ViewHolder
        }

        val name1 = getItem(position) as Subject
        holder.subject!!.setText(name1.subject)
        //holder.link!!.setText(name1.link)

        return convertView
    }

    // 뷰 홀더 패턴
    internal class ViewHolder {
        var subject: TextView? = null
        //var link: TextView? = null
    }
}