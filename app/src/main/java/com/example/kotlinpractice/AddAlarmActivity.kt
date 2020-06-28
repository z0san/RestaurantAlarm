package com.example.kotlinpractice

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.add_alarm.*

class AddAlarmActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_alarm)

        submitButton.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }


}