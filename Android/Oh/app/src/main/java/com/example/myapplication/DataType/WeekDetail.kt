package com.example.myapplication.DataType


class WeekDetail(var title:String, var data: String, var link : ArrayList<String?>, var linkname : ArrayList<String?> )  {



    override fun toString(): String { //이런 형식으로 문자열을 재배치 해주는 클래스(서버에서 gson형식으로 정보를 받아오면 이것을 우리가 원하는 데이터 형식으로 바꿔 줘야함)

        val sb = StringBuffer()
        sb.append(title).append('\'')
        sb.append(data).append('\'')
        sb.append(link).append('\'')
        sb.append(linkname).append('\'')

        return sb.toString()
    }
}