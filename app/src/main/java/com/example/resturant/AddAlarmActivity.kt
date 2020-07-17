package com.example.resturant

import android.app.AlertDialog
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.text.Html
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import dev.sasikanth.colorsheet.ColorSheet
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.add_alarm.*
import java.io.File

class AddAlarmActivity: AppCompatActivity() {

    var currentAlarm: AlarmType = AlarmType()
    var mediaPlayer: MediaPlayer? = null

    var selectedSound: File? = null//null for no sound a sound is not manditory to setup an alarm
    var selectedSoundBox: LinearLayout? = null

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

            //set the current sound to whatever is selected by the user
            if (selectedSound != null) currentAlarm.sound = selectedSound

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

    override fun onResume() {
        super.onResume()
        //remove all recordings from view
        recordingListChild.removeAllViews()//this prevents sounds being added more than once

        //get saved recordings
        val files: Array<File>? = this.filesDir.listFiles()
        if (files != null) {
            for(file:File in files){
                if(file.name!="tempRecording.3gp"){//ensure that this is not the temp file
                    if(file.name.substring(file.name.length-3)=="3gp")
                    {
                        //all saved recordings
                        val soundContainer: LinearLayout = LinearLayout(this)

                        //add name
                        val nameText: TextView = TextView(this)
                        nameText.text=file.nameWithoutExtension
                        val nameParams: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
                            ConstraintLayout.LayoutParams.WRAP_CONTENT,
                            ConstraintLayout.LayoutParams.WRAP_CONTENT
                        )
                        nameParams.weight=1f
                        nameText.layoutParams=nameParams
                        soundContainer.addView(nameText)

                        //play or stop button
                        val playButton: Button = Button(this)
                        playButton.text="PLAY"
                        playButton.setOnClickListener {
                            //play audio
                            if(playButton.text=="PLAY") {
                                playButton.text="STOP"
                                mediaPlayer = MediaPlayer()

                                //set listener for finished
                                mediaPlayer!!.setOnCompletionListener(MediaPlayer.OnCompletionListener {
                                    //when finisehd player
                                    playButton.text = "PLAY"
                                    mediaPlayer?.stop()
                                    mediaPlayer?.release()
                                })

                                //setup
                                mediaPlayer!!.setDataSource(file.absolutePath)
                                mediaPlayer!!.prepare()
                                mediaPlayer!!.start()
                            }
                            else
                            {
                                playButton.text="PLAY"
                                mediaPlayer?.stop()
                                mediaPlayer?.release()
                                mediaPlayer=null
                            }
                        }
                        soundContainer.addView(playButton)

                        //set sound container onclick listener
                        //this selects the sound
                        soundContainer.setOnClickListener{
                            if(selectedSoundBox!=soundContainer) {
                                selectedSound = file
                                //decolor other selected box

                                selectedSoundBox?.setBackgroundColor(0x00000000.toInt())
                                // this will not happen if selectedSoundBox is null

                                selectedSoundBox = soundContainer
                                soundContainer.setBackgroundColor(0xFFCCCCCC.toInt())
                            }
                            else{
                                //deselect current
                                selectedSound=null
                                selectedSoundBox=null
                                soundContainer.setBackgroundColor(0x00000000.toInt())
                            }
                        }

                        //set sound container onlongclick listener
                        //the deletes the sound
                        soundContainer.setOnLongClickListener {

                            //an alert box confirming the delete
                            //this builder is used to setup the dialogue box
                            val builder: AlertDialog.Builder= AlertDialog.Builder(this)
                                .setMessage(
                                    Html.fromHtml(
                                    "Are you sure you want to delete the sound: <b>"
                                            + file.nameWithoutExtension + "</b> ?"))
                                .setCancelable(false)//prevents calculation
                                //yes button deletes alarm
                                .setPositiveButton("yes"
                                ) { _, _ -> //delete alarm here

                                    //delete sound

                                    recordingListChild.removeView(soundContainer)
                                    //delete file
                                    file.delete()//this may fail due to permissions but will not throw
                                }
                                //no button does nothing
                                .setNegativeButton("no"
                                ) { di, _ -> //this closes the message box
                                    di.cancel()
                                }
                            builder.create().show()

                            true //this allows the UI to show the long click animation
                        }

                        recordingListChild.addView(soundContainer)
                    }
                }
            }
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