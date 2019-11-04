package com.example.myapplication

import android.os.Parcel
import android.os.Parcelable


class Subject(var subject: String?, var link: String?) : Parcelable {
    override fun toString(): String { //이런 형식으로 문자열을 재배치 해주는거야 이클래스는

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