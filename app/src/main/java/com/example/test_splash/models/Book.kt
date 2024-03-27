package com.example.test_splash.models

import android.os.Parcel
import android.os.Parcelable

data class Book(
    val title: String = "",
    val author: String = "",
    val shelfNumber: String = "",
    val lastSeenOn: String = "",
    val bookIcon: String = ""
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(title)
        writeString(author)
        writeString(shelfNumber)
        writeString(lastSeenOn)
        writeString(bookIcon)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Book> {
        override fun createFromParcel(parcel: Parcel): Book {
            return Book(parcel)
        }

        override fun newArray(size: Int): Array<Book?> {
            return arrayOfNulls(size)
        }
    }
}