package com.example.resturant

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dev.sasikanth.colorsheet.ColorSheet
import kotlinx.android.synthetic.main.add_alarm.*

class AddAlarmActivity: AppCompatActivity() {

    var currentAlarm: AlarmType = AlarmType()
    var mediaPlayer: MediaPlayer? = null


    //loads a pre-exsisting alarm into this interface
    fun loadAlarm(alarm: AlarmType){
        this.currentAlarm=alarm
        //set background color of color selector button
        colorSelector.setBackgroundColor(alarm.color)
        alarmName.setText(alarm.name)
        alarmFrequency.setText(alarm.frequencyMin.toString())

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.add_alarm)

        //check for selected alarm
        if(MainActivity.selectedAlarm!=null){
            //load selected alarm
            loadAlarm(MainActivity.selectedAlarm!!)
        }

        //when all details are entered you can submit
        submitButton.setOnClickListener {

            //set the current alarm name to whatever is entered by the user
            if (alarmName.text.toString() != "") currentAlarm.name = alarmName.text.toString()

            //set the current alarm frequency to whatever is entered by the user
            if (alarmFrequency.text.toString() != "") currentAlarm.frequencyMin = alarmFrequency.text.toString().toInt()

            if (currentAlarm.isFull()) {
                //add new alarm to Alarms if it is a new alarm
                if(MainActivity.selectedAlarm==null) MainActivity.alarms.add(currentAlarm)

                //deselect alarm
                MainActivity.selectedAlarm=null

                //save alarms
                saveAlarms(this, MainActivity.alarms)

                //set the current activity to main activity
                startActivity(Intent(this, MainActivity::class.java))
        }
        }

        colorSelector.setOnClickListener {
            val colors = intArrayOf(0xFF82B926.toInt(), 0xFFA276EB.toInt(),
                0xFF6A3AB2.toInt(), 0xFF666666.toInt(), 0xFFFFFF00.toInt(),
                0xFF3C8D2F.toInt(), 0xFFFA9F00.toInt(), 0xFFFF0000.toInt(),
                0xFFF78F8F.toInt(), 0xFF5454FF.toInt(), 0xFF6BC3DB.toInt(),
                0xFF754D27.toInt()
            )

            ColorSheet().colorPicker(
                colors = colors,
                listener = { color ->
                    currentAlarm.color = color
                    colorSelector.setBackgroundColor(color)
                })
                .show(supportFragmentManager)
        }

        newRecording.setOnClickListener {
            startActivity(Intent(this, SoundRecorder::class.java))
        }
    }


    public override fun onBackPressed(){

        //if updating alarm save new updates
        if(MainActivity.selectedAlarm!=null)
        {
            //set the current alarm name to whatever is entered by the user
            if (alarmName.text.toString() != "") currentAlarm.name = alarmName.text.toString()

            //set the current alarm frequency to whatever is entered by the user
            if (alarmFrequency.text.toString() != "") currentAlarm.frequencyMin = alarmFrequency.text.toString().toInt()
        }

        //if creating a new alarm
        //nothing needs to be done

        //clear selected alarm
        MainActivity.selectedAlarm=null;
        //save alarms
        saveAlarms(this, MainActivity.alarms)
        //set the current activity to main activity
        startActivity(Intent(this, MainActivity::class.java))
    }

}