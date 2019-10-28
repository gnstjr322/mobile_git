package com.example.myapplication


class Name(var name:String, var link: String, var day: String)  {

    /*constructor(name: String, link: String, day: String) {
        this.name = name
        this.link = link
        this.day = day
    }*/


    override fun toString(): String { //이런 형식으로 문자열을 재배치 해주는거야 이클래스는

        val sb = StringBuffer()
        sb.append(name).append('\'')
        sb.append(link).append('\'')
        sb.append(day).append('\'')

        return sb.toString()
    }
}