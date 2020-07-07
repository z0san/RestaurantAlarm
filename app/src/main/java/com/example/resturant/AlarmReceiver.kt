package com.example.resturant

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import java.util.*


class AlarmReceiver : BroadcastReceiver() {

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onReceive(context: Context, intent: Intent) {
//        val message = "Alarm received"
//        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        scheduleJob(context)
    }

    //function to set an update in the future so that the alarms will be updated at that time
    fun setAlarm(context: Context) {
        Log.d("Carbon", "Alarm SET !!")

        // get a Calendar object with current time
        val cal: Calendar = Calendar.getInstance()
        // add 5 seconds to the calendar object
        cal.add(Calendar.SECOND, 5)
        val intent = Intent(context, AlarmReceiver::class.java)
        val sender = PendingIntent.getBroadcast(context, 192837, intent,
                PendingIntent.FLAG_UPDATE_CURRENT)

        // Get the AlarmManager service
        val am: AlarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        am.set(AlarmManager.RTC_WAKEUP, cal.timeInMillis, sender)
    }

    //function to get all alarms that have gone off
    fun getTriggeredAlarms(): MutableList<AlarmType> {
        //where we store all the alarms that are currently going off
        var currentlyTriggered: MutableList<AlarmType> = mutableListOf()

        for(alarm in MainActivity.alarms) {
            //only check if alarm is turned on
            if (alarm.isOn) {

                var triggerTime: Calendar = Calendar.getInstance()

                //set the time to the last time the alarm went off
                triggerTime.timeInMillis = alarm.lastAlarm

                //find the time at which the time should go off so that it can be compared to the current time
                Log.e("last alarm: ", triggerTime.timeInMillis.toString())
                Log.e("current time: ", Calendar.getInstance().timeInMillis.toString())

                triggerTime.add(Calendar.SECOND, 5) //FOR TESTING ONLY

                //triggerTime.add(Calendar.MINUTE, alarm.frequencyMin)
                //if we are past the trigger time then add it to currently triggered
                if (triggerTime.timeInMillis <= Calendar.getInstance().timeInMillis) {
                    currentlyTriggered.add((alarm))
                }
            }
        }

        return currentlyTriggered
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun scheduleJob(context: Context) {
        val serviceComponent = ComponentName(context, AlarmJobs::class.java)
        val builder = JobInfo.Builder(0, serviceComponent)
        builder.setMinimumLatency(1.toLong()) // wait at least
        builder.setOverrideDeadline(1 * 1000.toLong()) // maximum delay
        //builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED); // require unmetered network
        //builder.setRequiresDeviceIdle(true); // device should be idle
        //builder.setRequiresCharging(false); // we don't care if the device is charging or not
        val jobScheduler: JobScheduler = context.getSystemService(JobScheduler::class.java)
        jobScheduler.schedule(builder.build())
    }
}