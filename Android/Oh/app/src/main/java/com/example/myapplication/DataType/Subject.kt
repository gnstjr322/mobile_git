package com.example.myapplication.DataType

import android.os.Parcel
import android.os.Parcelable


class Subject(var subject: String?, var link: String?) : Parcelable {
    override fun toString(): String { //이런 형식으로 문자열을 재배치 해주는 클래스(서버에서 gson형식으로 정보를 받아오면 이것을 우리가 원하는 데이터 형식으로 바꿔 줘야함)

        val sb = StringBuffer()
        sb.append(subject).append('\'')
        sb.append(link).append('\'')

        return sb.toString()
    }

    constructor(source: Parcel) : this(
            source.readString(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(subject)
        writeString(link)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Subject> = object : Parcelable.Creator<Subject> {
            override fun createFromParcel(source: Parcel): Subject = Subject(source)
            override fun newArray(size: Int): Array<Subject?> = arrayOfNulls(size)
        }
    }
}