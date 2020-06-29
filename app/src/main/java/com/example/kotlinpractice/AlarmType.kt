package com.example.kotlinpractice

import android.graphics.Color
import android.os.Build
import androidx.annotation.RequiresApi

class AlarmType {
    var frequencyMin: Int = 0
    var color: Int = 0
    var name: String = ""
    var isOn: Boolean = true

    fun isEmpty(): Boolean {
        return frequencyMin == 0 && color == 0 && name == ""
    }

    fun  isFull(): Boolean {
        return frequencyMin != 0 && color != 0 && name != ""
    }
}