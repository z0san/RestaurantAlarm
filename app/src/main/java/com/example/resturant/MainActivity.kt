package com.example.resturant

import android.app.AlarmManager
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*


class MainActivity: AppCompatActivity() {

    //global variables
    companion object {
        //stores all current alarms
        var alarms: MutableList<AlarmType> = mutableListOf()
        const val largeText = 48
        const val mediumText = 24
        const val smallText = 16
        var alarm_manager: AlarmManager? = null

        var currentlyTriggered: MutableList<AlarmType> = mutableListOf()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        Log.e("Working", "Main activity started!")
        alarm_manager = getSystemService(ALARM_SERVICE) as AlarmManager

        //load the alarms list with alarms if any have been saved
        val loadedAlarms: MutableList<AlarmType> = loadAlarms(this)

        //only add new alarms to the alarms list
        for(alarm in loadedAlarms) {
            if (!alarms.contains(alarm)) alarms.add(alarm)
        }

        //set button to go to make a new alarm
        addAlarmButton.setOnClickListener {
            startActivity(Intent(this, AddAlarmActivity::class.java))
        }


        //add test items
        for (alarm in alarms) {
            val alarmListView: AlarmListView = AlarmListView(this, alarm)

            //set alarmInfo on click as it needs to be in this scope
            alarmListView.setOnLongClickListener {
                //an alert box confirming the delete
                //this builder is used to setup the dialogue box
                val builder: AlertDialog.Builder= AlertDialog.Builder(this)
                    .setMessage(Html.fromHtml("Are you shure you want to delete the alarm: <b>" + alarm.name + "</b> ?"))
                    .setCancelable(false)//prevents cancilation
                    //yes button deletes alarm
                    .setPositiveButton("yes"
                    ) { _, _ -> //delete alarm here

                        //remove from view
                        alarmList.removeView(alarmListView)
                        MainActivity.alarms.remove(alarm)

                        //delete from storage
                        saveAlarms(this, MainActivity.alarms)
                    }
                    //no button does nothing
                    .setNegativeButton("no"
                    ) { di, _ -> //this closes the message box
                        di.cancel()
                    }
                builder.create().show()
                true
            }

            alarmList.addView(alarmListView)
        }
    }
}