package com.example.myapplication


class Name(var name:String, var link: String, var day: String)  {

    /*constructor(name: String, link: String, day: String) {
        this.name = name
        this.link = link
        this.day = day
    }*/


    override fun toString(): String {

        val sb = StringBuffer("Name{")
        sb.append("name='").append(name).append('\'')
        sb.append(", link='").append(link).append('\'')
        sb.append(", day='").append(day).append('\'')
        sb.append('}')


        /*val sb = StringBuffer("Weather{")
        sb.append("country='").append(name).append('\'')
        sb.append(", weather='").append(link).append('\'')
        sb.append(", temperature='").append(day).append('\'')
        sb.append('}')*/
        return sb.toString()
    }
}