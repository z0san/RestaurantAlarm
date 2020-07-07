package com.example.resturant

import android.app.AlarmManager
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
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
        var alarm_manager: AlarmManager? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        Log.e("Working", "Main activity started!")
        alarm_manager = getSystemService(ALARM_SERVICE) as AlarmManager

        //load the alarms list with alarms if any have been saved
        alarms = loadAlarms(this)

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
            alarmParams.setMargins(0, 0, 0, 0)

            alarmInfo.layoutParams = alarmParams

            //create a view that is the color of the alarm
            val colorLabel: View = View(this)

            //set params so that it sits properly
            val boxParams: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
                20,
                LinearLayout.LayoutParams.MATCH_PARENT
            )
            colorLabel.layoutParams = boxParams
            boxParams.setMargins(0, 0, 20, 0)

            colorLabel.setBackgroundColor(alarm.color)

            alarmInfo.addView((colorLabel))

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
            val context: Context = this//this is context

            //set params so that it sits properly
            val onOffParams: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            onOffSwitch.layoutParams = onOffParams

            //set the on click function for each alarm
            onOffSwitch.setOnClickListener{
                    alarm.onSwitchChange(this, onOffSwitch.isChecked)
            }

            //set the default switch state to whatever it is for the current alarm
            onOffSwitch.isChecked = alarm.isOn

            //set margins
            onOffParams.setMargins(30, 10, 10, 10)

            alarmInfo.addView(onOffSwitch)

            //set alarmInfo longclick
            //this will delete that alarm
            alarmInfo.isLongClickable=true
            alarmInfo.setOnLongClickListener {
                //an alert box confirming the delete
                //this builder is used to setup the dialogue box
                val builder: AlertDialog.Builder= AlertDialog.Builder(this)
                    .setMessage(Html.fromHtml("Are you shure you want to delete the alarm: <b>" + alarm.name + "</b> ?"))
                    .setCancelable(false)//prevents cancilation
                    //yes button deletes alarm
                    .setPositiveButton("yes",
                    object: DialogInterface.OnClickListener {
                        override fun onClick(di: DialogInterface, i: Int){
                            //delete alarm here

                            //remove from view
                            alarmList.removeView(alarmInfo)
                            alarms.remove(alarm)

                            //delete from storage
                            saveAlarms(context, alarms)
                        }
                    })
                    //no button does nothing
                    .setNegativeButton("no",
                    object: DialogInterface.OnClickListener {
                        override fun onClick(di: DialogInterface, i: Int){
                            //this closes the message box
                            di.cancel()
                        }
                    })
                builder.create().show()
                true
            }


            alarmList.addView(alarmInfo)
        }
    }
}