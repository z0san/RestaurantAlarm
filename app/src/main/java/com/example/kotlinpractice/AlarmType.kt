package com.example.kotlinpractice

import android.graphics.Color
import android.os.Build
import androidx.annotation.RequiresApi

class AlarmType {
    val FrequencyMin : Int = 0
    @RequiresApi(Build.VERSION_CODES.O)
    val AlarmColor : Color = Color.valueOf(0)
    val Name : String = ""
}