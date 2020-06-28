package com.example.kotlinpractice

import android.app.ActionBar
import android.content.Intent
import android.os.Bundle
import android.util.Log.d
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.children
import androidx.core.view.marginLeft
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        //set button to go to make a new alarm
        addAlarmButton.setOnClickListener {
            startActivity(Intent(this, AddAlarmActivity::class.java))
        }


        //add test items
        for (i in 0  until 50) {

            //horizontal layout for the alarm info
            val alarmInfo : LinearLayout = LinearLayout(this)

            //create label for the alarm
            val name : TextView = TextView(this)

            //set params so that it sits properly
            val nameParams : LinearLayout.LayoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            name.layoutParams = nameParams

            nameParams.weight = 1f

            name.text = "Alarm #$i"

            alarmInfo.addView(name)

            //create alarm frequency label
            val frequency : TextView = TextView(this)

            //set params so that it sits properly
            val freqParams : LinearLayout.LayoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            frequency.layoutParams = freqParams

            //freqParams.setMargins(100, 10, 10, 10)

            val min : Int = 30
            frequency.text = "Every : $min minutes"

            alarmInfo.addView(frequency)

            //create on off switch for the alarm
            val onOffSwitch : Switch = Switch(this)

            //set params so that it sits properly
            val onOffParams : LinearLayout.LayoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            onOffSwitch.layoutParams = onOffParams

            //set margins
            onOffParams.setMargins(30, 10, 10, 10)
            //shift it to the right
            //onOffParams.weight = 1f



            alarmInfo.addView(onOffSwitch)

            alarmList.addView(alarmInfo);
        }
    }
}