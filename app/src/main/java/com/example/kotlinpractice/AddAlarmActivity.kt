package com.example.kotlinpractice

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dev.sasikanth.colorsheet.ColorSheet
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.add_alarm.*

class AddAlarmActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_alarm)

        submitButton.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        colorSelector.setOnClickListener {
            val colors = intArrayOf(0xFF82B926.toInt(), 0xFFA276EB.toInt(),
                0xFF6A3AB2.toInt(), 0xFF666666.toInt(), 0xFFFFFF00.toInt(),
                0xFF3C8D2F.toInt(), 0xFFFA9F00.toInt(), 0xFFFF0000.toInt()
            )

            ColorSheet().colorPicker(
                colors = colors,
                listener = { color ->
                    // Handle color
                })
                .show(supportFragmentManager)
        }


    }


}