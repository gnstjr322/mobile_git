package com.example.myapplication

import android.os.Parcel
import android.os.Parcelable


class Cal(var date: String?, var mon: String?, var tue: String?, var wed: String?, var thu: String?, var fri: String?) : Parcelable {
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
        val CREATOR: Parcelable.Creator<Cal> = object : Parcelable.Creator<Cal> {
            override fun createFromParcel(source: Parcel): Cal = Cal(source)
            override fun newArray(size: Int): Array<Cal?> = arrayOfNulls(size)
        }
    }
}