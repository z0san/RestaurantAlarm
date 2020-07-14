package com.example.resturant

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.text.Html
import android.view.View
import android.widget.LinearLayout
import android.widget.Switch
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.content_main.*

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
            200,
            70 // hard coded for now but this needs to be changed just for visualisation now
        )

        progressBar.setBackgroundColor(0xFFCCCCCC.toInt())

        progressBar.layoutParams = progressParams

        addView(progressBar)


        //add main alarm info
        addView(makeAlarmInfo())



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
        }

        //set the default switch state to whatever it is for the current alarm
        onOffSwitch.isChecked = alarm.isOn

        //set margins
        onOffParams.setMargins(30, 10, 10, 10)

        MainActivity.switches.add(onOffSwitch)
        alarmInfo.addView(onOffSwitch)

        //this will delete that alarm
        isLongClickable=true

        //method is set in main activity

        return alarmInfo
    }
}