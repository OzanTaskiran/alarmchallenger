package com.example.alarmchallenger.ui

import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.updateLayoutParams
import androidx.lifecycle.ViewModelProvider
import com.example.alarmchallenger.R
import com.example.alarmchallenger.viewmodel.ChallengeShakeViewModel
import kotlinx.android.synthetic.main.activity_challenge_shake.*
import java.util.*


class ChallengeShakeActivity : AppCompatActivity() {

    private var sensorManager: SensorManager? = null
    lateinit  var challengeShakeViewModel: ChallengeShakeViewModel
    lateinit var progress: TextView
    private var maxHeight: Int = 0
    private var tickHeight: Int = 0

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_challenge_shake)
        progress = tvProgress

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        Objects.requireNonNull(sensorManager)!!.registerListener(sensorListener, sensorManager!!
            .getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL)

        challengeShakeViewModel = ViewModelProvider(this).get(ChallengeShakeViewModel::class.java)

        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
         maxHeight = displayMetrics.heightPixels
         tickHeight = maxHeight / 100

        challengeShakeViewModel.shakedValue.observe(this, androidx.lifecycle.Observer {

            backgroundProgress.updateLayoutParams {
                height += tickHeight
            }
            if(it == 100){
                var intent = Intent(this, AlarmActivity::class.java)
                startActivity(intent)
            }
            progress.text = it.toString()
        })

    }







    private val sensorListener: SensorEventListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent) {
            challengeShakeViewModel.calculate(event)
        }
        override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}
    }




    override fun onResume() {
        sensorManager?.registerListener(sensorListener, sensorManager!!.getDefaultSensor(
            Sensor .TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL
        )
        super.onResume()
    }

    override fun onPause() {
        sensorManager!!.unregisterListener(sensorListener)
        super.onPause()
    }

}

