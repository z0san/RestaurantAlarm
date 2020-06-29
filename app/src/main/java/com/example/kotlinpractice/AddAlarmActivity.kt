package com.example.kotlinpractice

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dev.sasikanth.colorsheet.ColorSheet
import kotlinx.android.synthetic.main.add_alarm.*

class AddAlarmActivity: AppCompatActivity() {

    var currentAlarm: AlarmType = AlarmType()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_alarm)

        //when all details are entered you can submit
        submitButton.setOnClickListener {

            //set the current alarm name to whatever is entered by the user
            currentAlarm.name = alarmName.text.toString()

            //set the current alarm frequency to whatever is entered by the user
            currentAlarm.frequencyMin = alarmFrequency.text.toString().toInt()

            if (currentAlarm.isFull()) {
                //add new alarm to Alarms
                MainActivity.alarms.add(currentAlarm)

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
    }


}