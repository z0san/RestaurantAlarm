package com.example.resturant

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.children
import kotlinx.android.synthetic.main.alarm_triggered.*
import org.w3c.dom.Text

class AlarmTriggered : AppCompatActivity() {

    //global variables
    companion object {
        //stores all the counts that we need to update every second
        var alarmViews: MutableSet<AlarmTriggeredView> = mutableSetOf<AlarmTriggeredView>()
    }

    // Create the Handler object (on the main thread by default)
    val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.alarm_triggered)

        //draw all alarms that are triggered
        drawAlarms(AlarmReceiver().getTriggeredAlarms())

        // Start the initial runnable task by posting through the handler
        // St   art the initial runnable task by posting through the handler
        handler.post(getRunnableLoop())

    }

    //function to generate teh runnable code that runs every second to update time since triggered
    private fun getRunnableLoop(): Runnable {
        // Create the Handler object (on the main thread by default)
        return object : Runnable {
            override fun run() {
                // Do something here on the main thread
                Log.d("Handlers", "Updating reverse counters")

                for(alarmView in alarmViews) {
                    alarmView.updateCount()
                }

                // Repeat this the same runnable code block again another 2 seconds
                // 'this' is referencing the Runnable object
                handler.postDelayed(this, 1000)
            }
        }
    }

    private fun drawAlarms(currentlyTriggered: MutableList<AlarmType>) {
        //draw all the alarms that are in currently triggered
        for (alarm in currentlyTriggered) {
            val triggeredAlarm: AlarmTriggeredView = AlarmTriggeredView(this, alarm)
            //add the triggered alarm to the page
            alarmLayout.addView(triggeredAlarm.alarmView)
            //add the triggered alarm to the list of alarm views
            alarmViews.add(triggeredAlarm)
        }
    }
}