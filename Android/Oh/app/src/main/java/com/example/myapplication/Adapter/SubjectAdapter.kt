package com.example.myapplication.Adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.myapplication.DataType.Subject
import com.example.myapplication.R

class SubjectAdapter(var sList: List<Subject>) : BaseAdapter() { //탭프레그먼트 3에서 과목 리스트 보여줌(스피너로)


    override fun getCount(): Int {
        return sList.size
    }

    override fun getItem(position: Int): Any {
        return sList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
        var convertView = convertView
        val holder: ViewHolder
        if (convertView == null) {
            convertView =
                    LayoutInflater.from(parent.context).inflate(R.layout.item_subject, parent, false)

            holder = ViewHolder()
            holder.subject = convertView.findViewById(R.id.subject_text) as TextView

            convertView.tag = holder
        } else {
            holder = convertView.tag as ViewHolder
        }

        val name1 = getItem(position) as Subject
        holder.subject?.setText(name1.subject)


        return convertView
    }

    // 뷰 홀더 패턴
    internal class ViewHolder {
        var subject: TextView? = null
        //var link: TextView? = null
    }
}