package com.example.resturant

class AlarmType {
    var frequencyMin: Int = 30
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