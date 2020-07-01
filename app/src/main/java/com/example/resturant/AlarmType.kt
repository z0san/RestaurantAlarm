package com.example.resturant

import android.content.Context
import java.io.*

class AlarmType: Serializable {
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

val fileName: String = "alarms.txt"

//function to retrieve alarms from file system
fun loadAlarms(context: Context): MutableList<AlarmType>{
    val alarms: MutableList<AlarmType> = mutableListOf()
    var file: FileInputStream? = null
    //catch if no file exists
    try {
        file = FileInputStream(File(context.filesDir, fileName))
    } catch (e: Exception){
        //in case of failure return empty now but later create file
        MessageBox().show(context, "Setting up", "Setting up files now")
//        val newFile = File(context.filesDir, fileName)
//        newFile.createNewFile()
        return alarms
    }

    //test for EOF in which case just return what has been successfully read from the file
    try {
        var inStream: ObjectInputStream = ObjectInputStream(file)
        var item: AlarmType? = inStream.readObject() as AlarmType?
        while (item != null){
            alarms.add(item)
            item = inStream.readObject() as AlarmType
        }

        inStream.close()
        file.close()
    } catch (e: EOFException){
        return alarms;
    }


    return alarms
}

//function to save alarms to file system
fun saveAlarms(context: Context, alarms: MutableList<AlarmType>): Unit{
    var file: FileOutputStream? = null
    try {
        file = FileOutputStream(File(context.filesDir, fileName))
    } catch (e: Exception){
        //in case of failure opening file return for now but later message box error
        MessageBox().show(context, "ERROR", "An error has occurred, " +
                "pleas restart your app!")
        return
    }
    val outStream: ObjectOutputStream = ObjectOutputStream(file)

    for (item in alarms){
        outStream.writeObject(item)
    }

    outStream.close()
    file.close()
}