package com.example.todo_main

import android.app.Application
import android.os.Parcel
import android.os.Parcelable

@MyApplication.HiltAndroidApp
class MyApplication() : Application(), Parcelable {
    annotation class HiltAndroidApp

    constructor(parcel: Parcel) : this() {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MyApplication> {
        override fun createFromParcel(parcel: Parcel): MyApplication {
            return MyApplication(parcel)
        }

        override fun newArray(size: Int): Array<MyApplication?> {
            return arrayOfNulls(size)
        }
    }
}