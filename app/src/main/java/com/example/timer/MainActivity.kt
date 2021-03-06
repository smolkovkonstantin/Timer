package com.example.timer

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import kotlin.io.path.createTempDirectory


class MainActivity : AppCompatActivity() {

    private fun toint(x: EditText?): Int {
        if (x?.text.contentEquals(""))
            return 0
        else
            return x?.text.toString().toInt()
    }

    @SuppressLint("SetTextI18n")
    private fun countdown(allTime: List<Int>) {
        runOnUiThread {
            if (allTime[0] < 10) second?.setText("0${allTime[0]}")
            else
                second?.setText(allTime[0].toString())

            if (allTime[1] < 10) minute?.setText("0${allTime[1]}")
            else
                minute?.setText(allTime[1].toString())

            if (allTime[2] < 10) hour?.setText("0${allTime[2]}")
            else
                hour?.setText(allTime[2].toString())
        }
    }

    private fun secondTominuteTohour(second: Int, minute: Int, hour: Int): List<Int> {
        return mutableListOf(second % 60, (minute + second / 60) % 60, hour + minute / 60)
    }

    private fun sixty(timeStart: MutableList<Int>): MutableList<Int> {
        if (timeStart[1] == 0 && timeStart[2] > 0) {
            if (timeStart[2] > 0) timeStart[1] = 60
            timeStart[2] -= 1
        }
        if (timeStart[0] == 0 && timeStart[1] > 0) {
            if (timeStart[1] > 0) timeStart[0] = 60
            timeStart[1] -= 1

            if (timeStart[1] == 0 && timeStart[2] > 0) {
                if (timeStart[2] > 0) timeStart[1] = 60
                timeStart[2] -= 1
            }
        }
        timeStart[0] -= 1
        return timeStart
    }

    private var hour: EditText? = null
    private var minute: EditText? = null
    private var second: EditText? = null
    private var go: Button? = null
    private var stopAndclear: Button? = null

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        go = findViewById(R.id.start)
        stopAndclear = findViewById(R.id.stopAndclear)

        second = findViewById(R.id.seconds)
        minute = findViewById(R.id.minutes)
        hour = findViewById(R.id.hours)

        go?.setOnClickListener {

            if (arrayOf(hour, minute, second).all { it?.text.contentEquals("") }) {
                Toast.makeText(this, "???????????????? ??????????", Toast.LENGTH_SHORT).show()
            } else {
                if (stopAndclear?.text == "CLEAR") stopAndclear?.text = "STOP"
                var timeStart = secondTominuteTohour(
                    toint(second),
                    toint(minute),
                    toint(hour)
                ).toMutableList()
                var flagCountdown = true
                val myThread = Thread { // ???????????????? ???????????? ????????????
                    while (timeStart.any { it > 0 }) {
                        if (!flagCountdown) break
                        // ???????????? ???????????? ?? ???????????????????????????? ?????????? minute ?? hour
                        timeStart = sixty(timeStart)
                        // ?????????? ??????????????????, ?? ?????????????? ???????? ?????????????? ?? UI
                        countdown(timeStart)
                        // ?????????????????? ???????????? myThread
                        Thread.sleep(1000)

                        stopAndclear?.setOnClickListener {
                            if (stopAndclear?.text == "CLEAR") {
                                stopAndclear?.text = "STOP"
                                second?.setText("")
                                minute?.setText("")
                                hour?.setText("")
                            }
                            if (stopAndclear?.text == "STOP") {
                                flagCountdown = false
                                stopAndclear?.text = "CLEAR"
                            }
                        }
                    }
                }
                myThread.start()
            }

        }

    }
}

