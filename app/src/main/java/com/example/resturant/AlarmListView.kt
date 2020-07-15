package com.example.resturant

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.Switch
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import java.util.*

class AlarmListView : ConstraintLayout{
    var alarm: AlarmType = AlarmType()

    //create on off switch for the alarm
    var onOffSwitch: Switch = Switch(context)

    //create alarm frequency label
    var frequency: TextView = TextView(context)

    //create label for the alarm
    var name: TextView = TextView(context)

    //create a view that is the color of the alarm
    var colorLabel: View = View(context)

    //create a view that will show the time until the alarm goes off
    val progressBar: View = View(context)

    //initiate handler
    val progressBarHandler: Handler = Handler()


    //horizontal layout for the alarm info
    //var alarmInfo: LinearLayout = LinearLayout(context)

    constructor(context: Context, alarm: AlarmType) : super(context) {
        this.alarm = alarm

        //set params so that it sits properly
        val alarmParams: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
            LayoutParams.MATCH_PARENT,
            LayoutParams.MATCH_PARENT
        )
        alarmParams.setMargins(0, 0, 0, 0)

        layoutParams = alarmParams

        //add progress bar

        //set params so that it sits properly
        val progressParams: ConstraintLayout.LayoutParams = ConstraintLayout.LayoutParams(
            0, 0 // hard coded for now but this needs to be changed just for visualisation now
        )

        progressBar.setBackgroundColor(0xFFCCCCCC.toInt())

        progressBar.layoutParams = progressParams

        addView(progressBar)

        //add main alarm info
        val alarmInfo: View = makeAlarmInfo()
        addView(alarmInfo)

        // Start the initial runnable task by posting through the handler
        progressBarHandler.post(getRunnableLoop(progressBar, alarm, alarmInfo))

    }

    //function to generate the runnable code that runs intermittently to update progress bar
    //will update the progress bar's width to the proportion of time until the alarm goes off
    private fun getRunnableLoop(progressBar: View, alarm: AlarmType, alarmInfo: View): Runnable {

        // Create the Handler object (on the main thread by default)
        return object : Runnable {
            override fun run() {
                // Do something here on the main thread
                Log.d("Handlers", "Updating progress bars")

                //if alarm is turned off set width to 0 and return
                if (!alarm.isOn) {
                    progressBar.layoutParams = LayoutParams(0, 0)
                    return
                }
                val cal: Calendar = Calendar.getInstance()
                val width: Double = alarmInfo.width *
                        ((cal.timeInMillis - alarm.lastAlarm) / 60000.0) / alarm.frequencyMin

                progressBar.layoutParams = LayoutParams(width.toInt(), alarmInfo.height)

                // Repeat this the same runnable code block again another 2 seconds
                // 'this' is referencing the Runnable object
                handler.postDelayed(this, 100)
            }
        }
    }

    //responsible for making the alarm info linear layout
    private fun makeAlarmInfo(): LinearLayout {
        val alarmInfo: LinearLayout = LinearLayout(context)

        //set background to clear
        alarmInfo.setBackgroundColor(0x00000000.toInt())

        onOffSwitch = Switch(context)
        frequency = TextView(context)
        name = TextView(context)
        colorLabel = View(context)

        //set params so that it sits properly
        val alarmInfoParams: ConstraintLayout.LayoutParams = ConstraintLayout.LayoutParams(
            LayoutParams.MATCH_PARENT,
            LayoutParams.MATCH_PARENT
        )
        alarmInfoParams.setMargins(0, 0, 0, 0)

        alarmInfo.layoutParams = alarmInfoParams

        //set params so that it sits properly
        val boxParams: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
            20,
            LayoutParams.MATCH_PARENT
        )
        colorLabel.layoutParams = boxParams
        boxParams.setMargins(0, 0, 20, 0)

        colorLabel.setBackgroundColor(alarm.color)

        alarmInfo.addView((colorLabel))

        //set params so that it sits properly
        val nameParams: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
            LayoutParams.WRAP_CONTENT,
            LayoutParams.WRAP_CONTENT
        )
        name.layoutParams = nameParams

        nameParams.weight = 1f
        name.textSize = MainActivity.smallText.toFloat()
        name.text = alarm.name
        //set it to bold
        name.typeface = Typeface.DEFAULT_BOLD
        name.setTextColor(Color.parseColor("#000000"))

        alarmInfo.addView(name)



        //set params so that it sits properly
        val freqParams: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
            LayoutParams.WRAP_CONTENT,
            LayoutParams.WRAP_CONTENT
        )
        frequency.layoutParams = freqParams
        //freqParams.setMargins(100, 10, 10, 10)

        frequency.textSize = MainActivity.smallText.toFloat()
        frequency.text = "Every : ${alarm.frequencyMin} minutes"

        alarmInfo.addView(frequency)



        //set params so that it sits properly
        val onOffParams: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
            LayoutParams.WRAP_CONTENT,
            LayoutParams.WRAP_CONTENT
        )
        onOffSwitch.layoutParams = onOffParams

        //set the on click function for each alarm
        onOffSwitch.setOnClickListener{
            alarm.onSwitchChange(context, onOffSwitch.isChecked)
            // Start the initial runnable task by posting through the handler
            progressBarHandler.post(getRunnableLoop(progressBar, alarm, alarmInfo))
        }

        //set the default switch state to whatever it is for the current alarm
        onOffSwitch.isChecked = alarm.isOn

        //set margins
        onOffParams.setMargins(30, 10, 10, 10)

        alarmInfo.addView(onOffSwitch)

        MainActivity.switches.add(onOffSwitch)

        //this will delete that alarm
        isLongClickable=true

        //method is set in main activity

        return alarmInfo
    }
}