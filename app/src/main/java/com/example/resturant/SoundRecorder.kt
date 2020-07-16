package com.example.resturant

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.media.MediaPlayer.OnCompletionListener
import android.media.MediaRecorder
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.sound_recorder.*
import java.io.File


private const val REQUEST_RECORD_AUDIO_PERMISSION = 200

class SoundRecorder: AppCompatActivity() {
    private var mediaRecorder: MediaRecorder?=null
    private var mediaPlayer: MediaPlayer?=null

    //permissions stuff
    private var permissionToRecordAccepted = false
    private var permissions: Array<String> = arrayOf(Manifest.permission.RECORD_AUDIO)

    private var playing: Boolean=false

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.sound_recorder)
        endButton.isEnabled=false
        startButton.isEnabled=false
        saveButton.isEnabled=false
        playButton.isEnabled=false
        //request permission
        requestPermissions( permissions, REQUEST_RECORD_AUDIO_PERMISSION)

        startButton.setOnClickListener{


            //setup media recorder
            mediaRecorder= MediaRecorder()
            mediaRecorder!!.setAudioSource(MediaRecorder.AudioSource.MIC)
            mediaRecorder!!.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            mediaRecorder!!.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB)
            mediaRecorder!!.setOutputFile(File(this.filesDir, "tempRecording.3gp"))
            //record
            mediaRecorder!!.prepare()
            mediaRecorder!!.start()

            startButton.setEnabled(false)
            endButton.setEnabled(true)
        }
        endButton.setOnClickListener{
            mediaRecorder?.stop()
            mediaRecorder?.release()
            endButton.isEnabled=false
            startButton.isEnabled=true
            saveButton.isEnabled=true
            playButton.isEnabled=true
        }
        saveButton.setOnClickListener{
            if(checkName()){
                File(this.filesDir,"tempRecording.3gp").renameTo(File(this.filesDir,soundName.text.toString()+".3fp"))
            }
            saveButton.isEnabled=false;
        }
        cancelButton.setOnClickListener{
            startActivity(Intent(this, AddAlarmActivity::class.java))
        }
        playButton.setOnClickListener{
            if(!playing) {
                playButton.text="STOP"
                mediaPlayer = MediaPlayer()

                //set listener for finished
                mediaPlayer!!.setOnCompletionListener(OnCompletionListener {
                    //when finisehd player
                    playButton.text="PLAY"
                    mediaPlayer?.stop()
                    mediaPlayer?.release()
                    playing=false
                })

                //setup
                mediaPlayer!!.setDataSource(File(this.filesDir, "tempRecording.3gp").absolutePath)
                mediaPlayer!!.prepare()
                mediaPlayer!!.start()
            }
            else
            {
                playButton.text="PLAY"
                mediaPlayer?.stop()
                mediaPlayer?.release()
            }
            playing=!playing
        }
    }

    private fun checkName():Boolean{
        //TODO check for overridden sounds
        return soundName.text.toString()!=""
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissionToRecordAccepted = if (requestCode == REQUEST_RECORD_AUDIO_PERMISSION) {
            grantResults[0] == PackageManager.PERMISSION_GRANTED
        } else {
            false
        }
        if(permissionToRecordAccepted) startButton.isEnabled=true
    }

    override fun onStop() {
        super.onStop()
        mediaRecorder?.release()
        mediaRecorder=null
        mediaPlayer?.release()
        mediaPlayer=null
    }
}