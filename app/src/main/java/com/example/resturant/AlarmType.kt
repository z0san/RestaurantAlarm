package com.example.resturant

import android.content.Context
import java.io.*
import java.util.*

class AlarmType: Serializable {
    var frequencyMin: Int = 30
    var color: Int = 0
    var name: String = ""
    var isOn: Boolean = false
    var lastAlarm: Long = 0
    var sound: File? = null//null for no sound

    fun isEmpty(): Boolean {
        return frequencyMin == 0 && color == 0 && name == ""
    }

    fun  isFull(): Boolean {
        return frequencyMin != 0 && color != 0 && name != ""
    }

    //function to handle when a alarm is switched on or off
    fun onSwitchChange(context: Context, isChecked: Boolean) {
        isOn = isChecked
        if (isChecked) {
            //if checked then we need to start the alarm and reset the last alarm
            val cal: Calendar = Calendar.getInstance()
            lastAlarm = cal.timeInMillis
            //start the timer for the alarm
            AlarmReceiver().setAlarm(context, frequencyMin)
        }
        //we don't need to do anything if unchecked besides turn it off because then we know the
        //the alarm won't go off
    }

    //overwrite equals method so that it dosen't check for on off state
    override fun equals(other: Any?): Boolean {
        // If the object is compared with itself then return true
        if (other === this) {
            return true
        }

        /* Check if o is an instance of Complex or not
          "null instanceof [type]" also returns false */if (other !is AlarmType) {
            return false
        }

        // typecast o to Complex so that we can compare data members
        val c: AlarmType = other as AlarmType

        // Compare the data members and return accordingly
        return other.name == name && other.frequencyMin == frequencyMin && other.color == color
    }
}

const val fileName: String = "alarms.txt"

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
            //set to 0 by default so that main activity knows to overwrite this on first start up
            item.lastAlarm = 0

            //set all alarms off by default for now
            item.isOn = false;

            //testing ------------
//            AlarmReceiver().setAlarm(context)
//            MainActivity.currentlyTriggered.add(item)

            item = inStream.readObject() as AlarmType
        }

        inStream.close()
        file.close()
    } catch (e: EOFException){
        return alarms;
    } catch (e: InvalidClassException) {
        MessageBox().show(context, "Setting up", "Resetting up files")
        return alarms
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


