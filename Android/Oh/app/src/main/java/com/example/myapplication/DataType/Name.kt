package com.example.myapplication.DataType

import android.os.Parcel
import android.os.Parcelable


class Name(var form: String?, var name: String?, var link: String?, var day: String?) : Parcelable {
    //이런 형식으로 문자열을 재배치 해주는 클래스(서버에서 gson형식으로 정보를 받아오면 이것을 우리가 원하는 데이터 형식으로 바꿔 줘야함)
    override fun toString(): String { //이런 형식으로 문자열을 재배치 해주는거야 이클래스는

        val sb = StringBuffer()
        sb.append(form).append('\'')
        sb.append(name).append('\'')
        sb.append(link).append('\'')
        sb.append(day).append('\'')

        return sb.toString()
    }

    constructor(source: Parcel) : this(
            source?.readString(),
            source.readString(),
            source.readString(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(form)
        writeString(name)
        writeString(link)
        writeString(day)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Name> = object : Parcelable.Creator<Name> {
            override fun createFromParcel(source: Parcel): Name = Name(source)
            override fun newArray(size: Int): Array<Name?> = arrayOfNulls(size)
        }
    }
}