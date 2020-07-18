package com.example.resturant

import android.content.Context
import android.content.Intent
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat.startActivity
import java.util.*

class AlarmTriggeredView : ConstraintLayout {
    var alarm: AlarmType = AlarmType()
    //view to store all the alarms that have gone off
    //val alarmView: ConstraintLayout = ConstraintLayout(context)
    var dismissButton: Button = Button(context)
    private var count: TextView = TextView(context)
    private var title: TextView = TextView(context)

    constructor(context: Context, alarm: AlarmType) : super(context) {
        this.alarm = alarm


        //set params so that it sits properly
        val viewParams: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            0,
            1F
        )
        layoutParams = viewParams
        setBackgroundColor(alarm.color)


        //set params so that it sits properly
        val buttonParams: ConstraintLayout.LayoutParams = ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.WRAP_CONTENT,
            ConstraintLayout.LayoutParams.WRAP_CONTENT
        )

        buttonParams.bottomToBottom = 0
        buttonParams.endToEnd = 0
        buttonParams.topToTop = 0
        buttonParams.startToStart = 0
        buttonParams.horizontalBias = 0.5F
        buttonParams.verticalBias = 0.85F

        dismissButton.layoutParams = buttonParams
        dismissButton.text = "DISMISS"

        dismissButton.setOnClickListener {
            //reset alarm variables
            alarm.lastAlarm = Calendar.getInstance().timeInMillis
            AlarmReceiver().setAlarm(context, alarm.frequencyMin)

            //remove from currently triggered
            MainActivity.currentlyTriggered.remove(alarm)

            if (AlarmReceiver().getTriggeredAlarms().isEmpty()) {
                //if empty go back to main window
                startActivity(context, Intent(context, MainActivity::class.java), null)
            } else {
                //otherwise redraw current window
                startActivity(context, Intent(context, AlarmTriggered::class.java), null)
            }
        }

        addView(dismissButton)

        title.text = alarm.name

        val titleParams: ConstraintLayout.LayoutParams = ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.WRAP_CONTENT,
            ConstraintLayout.LayoutParams.WRAP_CONTENT
        )

        titleParams.bottomToBottom = 0
        titleParams.endToEnd = 0
        titleParams.topToTop = 0
        titleParams.startToStart = 0
        titleParams.verticalBias = 0.1F

        title.layoutParams = titleParams
        title.setTextColor(0xFF000000.toInt())
        title.textSize = 24F

        addView(title)


        count.text = "0"

        val countParams: ConstraintLayout.LayoutParams = ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.WRAP_CONTENT,
            ConstraintLayout.LayoutParams.WRAP_CONTENT
        )

        countParams.bottomToBottom = 0
        countParams.endToEnd = 0
        countParams.topToTop = 0
        countParams.startToStart = 0
        countParams.verticalBias = 0.375F

        count.layoutParams = countParams
        count.setTextColor(0x66000000.toInt())
        count.textSize = 18F

        addView(count)
    }

    //function to update the count
    fun updateCount() {
        //calendar to be set to the time of the alarm last alarm
        val cal: Calendar = Calendar.getInstance()
        cal.timeInMillis = alarm.lastAlarm

        //current time
        val current: Calendar = Calendar.getInstance()

        var value: Int = ((cal.time.seconds - current.time.seconds) %
                (alarm.frequencyMin * 60))

        if (value > 0) value -= (alarm.frequencyMin * 60)

        count.text = value.toString()
    }
}