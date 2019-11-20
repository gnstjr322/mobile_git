package com.example.myapplication


class Name2(var title:String, var data: String, var link : ArrayList<String?>, var linkname : ArrayList<String?> )  {



    override fun toString(): String { //이런 형식으로 문자열을 재배치 해주는거야 이클래스는

        val sb = StringBuffer()
        sb.append(title).append('\'')
        sb.append(data).append('\'')
        sb.append(link).append('\'')
        sb.append(linkname).append('\'')

        return sb.toString()
    }
}