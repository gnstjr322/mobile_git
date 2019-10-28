package com.example.myapplication


class Name2(var form:String, var txt:String, var data: String)  {



    override fun toString(): String { //이런 형식으로 문자열을 재배치 해주는거야 이클래스는

        val sb = StringBuffer()
        sb.append(form).append('\'')
        sb.append(txt).append('\'')
        sb.append(data).append('\'')


        return sb.toString()
    }
}