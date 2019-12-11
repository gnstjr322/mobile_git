package com.example.myapplication.DataType

import android.os.Parcel
import android.os.Parcelable


class Exam(var date: String?, var mon: String?, var tue: String?, var wed: String?, var thu: String?, var fri: String?) : Parcelable {
    override fun toString(): String { //이런 형식으로 문자열을 재배치 해주는 클래스(서버에서 gson형식으로 정보를 받아오면 이것을 우리가 원하는 데이터 형식으로 바꿔 줘야함)

        val sb = StringBuffer()
        sb.append(date).append('\'')
        sb.append(mon).append('\'')
        sb.append(tue).append('\'')
        sb.append(wed).append('\'')
        sb.append(thu).append('\'')
        sb.append(fri).append('\'')

        return sb.toString()
    }

    constructor(source: Parcel) : this(
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(date)
        writeString(mon)
        writeString(tue)
        writeString(wed)
        writeString(thu)
        writeString(fri)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Exam> = object : Parcelable.Creator<Exam> {
            override fun createFromParcel(source: Parcel): Exam = Exam(source)
            override fun newArray(size: Int): Array<Exam?> = arrayOfNulls(size)
        }
    }
}