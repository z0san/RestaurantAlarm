package com.example.resturant

import android.app.AlarmManager
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.util.TypedValue
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception


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
        var selectedAlarm: AlarmType? =null
        //holds the alarm that is currently selected, null if none are selected

        val switches: MutableList<Switch> = mutableListOf()
        //stores the switches for the alarms so they can be changed
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        Log.e("Working", "Main activity started!")
        alarm_manager = getSystemService(ALARM_SERVICE) as AlarmManager

        //set the padding on alarm list
//        var actionBarHeight = 0
//
//        val tv = TypedValue()
//        if (theme.resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
//            actionBarHeight =
//                TypedValue.complexToDimensionPixelSize(tv.data, resources.displayMetrics)
//        }
//
//        MessageBox().show(this, "height: ", actionBarHeight.toString())
//        alarmList.setPadding(0, actionBarHeight, 0, 0)

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
        /* Currently Disabled
        //set button to go to settings
        settingsButton.setOnClickListener{
            startActivity(Intent(this, SettingsActivity::class.java))
        }
        */
        //add universal off switch
        universalSwitch.setOnClickListener {
            for(alarm in alarms){//set the alarm objects to the switch value
                alarm.onSwitchChange(this,universalSwitch.isChecked)
            }
            //set all switches to closed
            for(switch in switches){
                switch.isChecked=universalSwitch.isChecked
            }
        }

        //add test items
        for (alarm in alarms) {
            val alarmListView: AlarmListView = AlarmListView(this, alarm)

            //set alarmInfo on click as it needs to be in this scope
            alarmListView.setOnLongClickListener {

                //an alert box confirming the delete
                //this builder is used to setup the dialogue box
                val builder: AlertDialog.Builder= AlertDialog.Builder(this)
                    .setMessage(Html.fromHtml(
                        "Are you sure you want to delete the alarm: <b>"
                                + alarm.name + "</b> ?"))
                    .setCancelable(false)//prevents calculation
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