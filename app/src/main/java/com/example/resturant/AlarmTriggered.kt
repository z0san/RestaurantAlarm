package com.example.resturant

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.alarm_triggered.*
import java.util.*

class AlarmTriggered : AppCompatActivity() {

    //global variables
    companion object {
        //stores all the counts that we need to update every second
        var alarmViews: MutableSet<AlarmTriggeredView> = mutableSetOf<AlarmTriggeredView>()
        var currentMediaPlayers: MutableMap<AlarmType, MediaPlayer> = mutableMapOf()
    }

    // Create the Handler object (on the main thread by default)
    val handler: Handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.alarm_triggered)

        val triggered: MutableList<AlarmType> = AlarmReceiver().getTriggeredAlarms()

        //draw all alarms that are triggered
        drawAlarms(triggered)

        //play all alarms that are triggered
        playAlarms(triggered)

        // Start the initial runnable task by posting through the handler
        // St   art the initial runnable task by posting through the handler
        handler.post(getRunnableLoop())

    }

    fun playAlarms(alarms: MutableList<AlarmType>) {
        for(alarm in alarms) {
            //play audio
            if(!alarm.playingSound && alarm.sound != null) {
                currentMediaPlayers[alarm] = MediaPlayer()

                alarm.playingSound = true

                //set listener for finished
                currentMediaPlayers[alarm]?.setOnCompletionListener(
                    MediaPlayer.OnCompletionListener {
                        //when finished player
                        currentMediaPlayers[alarm]?.stop()
                        currentMediaPlayers[alarm]?.release()
                        alarm.playingSound = false
//                        currentMediaPlayers[alarm]?.start()
                    }
                )

                //setup
                currentMediaPlayers[alarm]?.setDataSource(alarm.sound?.absolutePath)
                currentMediaPlayers[alarm]?.prepare()
                currentMediaPlayers[alarm]?.start()
            }
        }
    }

    //function to generate the runnable code that runs every second to update time since triggered
    private fun getRunnableLoop(): Runnable {
        // Create the Handler object (on the main thread by default)
        return object : Runnable {
            override fun run() {
                // Do something here on the main thread
                Log.d("Handlers", "Updating reverse counters")

                for(alarmView in alarmViews) {
                    alarmView.updateCount()
                }

                //replay any sounds that have finished
                playAlarms(AlarmReceiver().getTriggeredAlarms())

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
            alarmLayout.addView(triggeredAlarm)
            //add the triggered alarm to the list of alarm views
            alarmViews.add(triggeredAlarm)
        }
    }
}