package com.example.resturant

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity: AppCompatActivity() {

    //global variables
    companion object {
        //stores all current alarms
        var alarms : MutableList<AlarmType> = mutableListOf()
        const val largeText = 48
        const val mediumText = 24
        const val smallText = 16
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        //set button to go to make a new alarm
        addAlarmButton.setOnClickListener {
            startActivity(Intent(this, AddAlarmActivity::class.java))
        }


        //add test items
        for (alarm in alarms) {

            //horizontal layout for the alarm info
            val alarmInfo: LinearLayout = LinearLayout(this)

            //set params so that it sits properly
            val alarmParams: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
            )
            alarmParams.setMargins(20, 10, 10, 0)

            alarmInfo.layoutParams = alarmParams

            //create label for the alarm
            val name: TextView = TextView(this)

            //set params so that it sits properly
            val nameParams: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            name.layoutParams = nameParams

            nameParams.weight = 1f
            name.textSize = smallText.toFloat()
            name.text = alarm.name
            //set it to bold
            name.typeface = Typeface.DEFAULT_BOLD
            name.setTextColor(Color.parseColor("#000000"))

            alarmInfo.addView(name)

            //create alarm frequency label
            val frequency: TextView = TextView(this)

            //set params so that it sits properly
            val freqParams: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            frequency.layoutParams = freqParams
            //freqParams.setMargins(100, 10, 10, 10)

            frequency.textSize = smallText.toFloat()
            frequency.text = "Every : ${alarm.frequencyMin} minutes"

            alarmInfo.addView(frequency)

            //create on off switch for the alarm
            val onOffSwitch: Switch = Switch(this)

            //set params so that it sits properly
            val onOffParams: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            onOffSwitch.layoutParams = onOffParams

            //set the default switch state to whatever it is for the current alarm
            onOffSwitch.isChecked = alarm.isOn

            //set margins
            onOffParams.setMargins(30, 10, 10, 10)

            alarmInfo.addView(onOffSwitch)

            alarmList.addView(alarmInfo);
        }
    }
}