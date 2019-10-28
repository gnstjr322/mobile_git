package com.example.myapplication


class Cal(var date:String, var mon: String, var tue: String, var wed: String, var thu: String, var fri: String)  {

    /*constructor(name: String, link: String, day: String) {
        this.name = name
        this.link = link
        this.day = day
    }*/


    override fun toString(): String { //이런 형식으로 문자열을 재배치 해주는거야 이클래스는

        val sb = StringBuffer()
        sb.append(date).append('\'')
        sb.append(mon).append('\'')
        sb.append(tue).append('\'')
        sb.append(wed).append('\'')
        sb.append(thu).append('\'')
        sb.append(fri).append('\'')

        return sb.toString()
    }
}