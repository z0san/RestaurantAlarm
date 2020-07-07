package com.example.resturant

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.add_alarm.*
import kotlinx.android.synthetic.main.alarm_triggered.*
import java.util.*

class AlarmTriggered : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.alarm_triggered)

        //draw all alarms that are triggered
        drawAlarms(AlarmReceiver().getTriggeredAlarms())

    }

    private fun drawAlarms(currentlyTriggered: MutableList<AlarmType>) {
        //draw all the alarms that are in currently triggered
        for (alarm in currentlyTriggered) {

            //view to store all the alarms that have gone off
            var alarmView: ConstraintLayout = ConstraintLayout(this)
            //set params so that it sits properly
            val viewParams: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                0,
                1F
            )
            alarmView.layoutParams = viewParams
            alarmView.setBackgroundColor(alarm.color)

            var dismissButton: Button = Button(this)
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



            var title: TextView = TextView(this)
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

            var count: TextView = TextView(this)
            count.text = "-2"

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

            alarmLayout.addView(alarmView)
        }
    }
}