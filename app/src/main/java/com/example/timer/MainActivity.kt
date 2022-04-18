package com.example.timer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val go: Button = findViewById(R.id.start)
        val stopAndclear: Button = findViewById(R.id.clear)

        var flagOfstart = false

        go.setOnClickListener {
            val hour: EditText? = findViewById(R.id.hours)
            val minute: EditText? = findViewById(R.id.minutes)
            val second: EditText? = findViewById(R.id.seconds)

            if (arrayOf(hour, minute, second).all { it == null }) {
                Toast.makeText(this, "Засеките время", Toast.LENGTH_SHORT).show()
            } else{
                val timeStart = hour * 3600 + minute * 60 + second
                flagOfstart = true

                while (timeStart > 0){

                }
            }

        }

    }
}