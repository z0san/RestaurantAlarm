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
import android.widget.TextView
import androidx.core.view.children
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
            val view1 : TextView = TextView(this)

            view1.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

            view1.text = "TESTING : $i"

            alarmList.addView(view1);
        }

    }
}