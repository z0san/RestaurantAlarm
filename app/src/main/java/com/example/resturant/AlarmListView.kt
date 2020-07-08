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
import kotlinx.android.synthetic.main.content_main.*

class AlarmListView : LinearLayout{
    var alarm: AlarmType = AlarmType()

    //create on off switch for the alarm
    var onOffSwitch: Switch = Switch(context)

    //create alarm frequency label
    var frequency: TextView = TextView(context)

    //create label for the alarm
    var name: TextView = TextView(context)

    //create a view that is the color of the alarm
    var colorLabel: View = View(context)

    //horizontal layout for the alarm info
    //var alarmInfo: LinearLayout = LinearLayout(context)

    constructor(context: Context, alarm: AlarmType) : super(context) {
        this.alarm = alarm

        onOffSwitch = Switch(context)
        frequency = TextView(context)
        name = TextView(context)
        colorLabel = View(context)

        //set params so that it sits properly
        val alarmParams: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        alarmParams.setMargins(0, 0, 0, 0)

        layoutParams = alarmParams

        //set params so that it sits properly
        val boxParams: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
            20,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        colorLabel.layoutParams = boxParams
        boxParams.setMargins(0, 0, 20, 0)

        colorLabel.setBackgroundColor(alarm.color)

        addView((colorLabel))

        //set params so that it sits properly
        val nameParams: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        name.layoutParams = nameParams

        nameParams.weight = 1f
        name.textSize = MainActivity.smallText.toFloat()
        name.text = alarm.name
        //set it to bold
        name.typeface = Typeface.DEFAULT_BOLD
        name.setTextColor(Color.parseColor("#000000"))

        addView(name)



        //set params so that it sits properly
        val freqParams: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        frequency.layoutParams = freqParams
        //freqParams.setMargins(100, 10, 10, 10)

        frequency.textSize = MainActivity.smallText.toFloat()
        frequency.text = "Every : ${alarm.frequencyMin} minutes"

        addView(frequency)



        //set params so that it sits properly
        val onOffParams: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
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

        addView(onOffSwitch)

        //set alarmInfo longclick
        //this will delete that alarm
        isLongClickable=true


    }
}