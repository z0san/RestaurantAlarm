package com.example.resturant

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import java.util.*

class AlarmTriggeredView : View {
    var alarm: AlarmType = AlarmType()
    //view to store all the alarms that have gone off
    val alarmView: ConstraintLayout = ConstraintLayout(context)
    var dismissButton: Button = Button(context)
    var count: TextView = TextView(context)
    var title: TextView = TextView(context)

    constructor(context: Context, alarm: AlarmType) : super(context) {
        this.alarm = alarm


        //set params so that it sits properly
        val viewParams: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            0,
            1F
        )
        alarmView.layoutParams = viewParams
        alarmView.setBackgroundColor(alarm.color)


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
        alarmView.addView(dismissButton)




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

        alarmView.addView(title)


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

        alarmView.addView(count)
    }

    //function to update the count
    fun updateCount() {
        count.text = (((alarm.lastAlarm.toInt() / 1000) -
                (Calendar.getInstance().timeInMillis.toInt() / 1000)) % alarm.frequencyMin)
                .toString()
    }
}