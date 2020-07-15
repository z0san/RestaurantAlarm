package com.example.resturant

import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Intent
import android.widget.Toast


//used to schedule jobs
class AlarmJobs : JobService() {

    override fun onStartJob(params: JobParameters?): Boolean {

        val triggeredAlarms = AlarmReceiver().getTriggeredAlarms()
        //only start the alarm triggered activity if there are alarms that should be going off
        //also checks that there is a new alarm that has gon off
        if (triggeredAlarms.isNotEmpty() && triggeredAlarms != MainActivity.currentlyTriggered) {
            //set the currently triggered
            MainActivity.currentlyTriggered = triggeredAlarms

            //create the alarm triggered page after this timer goes off

            val message = "Alarm triggered"
            Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()

            val intent = Intent(applicationContext, AlarmTriggered::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            applicationContext.startActivity(intent)
        }
        return true
    }

    override fun onStopJob(params: JobParameters?): Boolean {
        return true
    }

}
