package com.example.resturant

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import java.util.*


class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        //val message = "Alarm triggered: " + alarm?.name.toString()
        //Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        val intent2 = Intent(context, AlarmTriggered::class.java)
        intent2.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent2)
    }

    fun setAlarm(context: Context) {
        Log.d("Carbon", "Alrm SET !!")

        // get a Calendar object with current time
        val cal: Calendar = Calendar.getInstance()
        // add 30 seconds to the calendar object
        cal.add(Calendar.SECOND, 5)
        val intent = Intent(context, AlarmReceiver::class.java)
        val sender =
            PendingIntent.getBroadcast(context, 192837, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        // Get the AlarmManager service
        val am =
            context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        am[AlarmManager.RTC_WAKEUP, cal.timeInMillis] = sender
    }
}