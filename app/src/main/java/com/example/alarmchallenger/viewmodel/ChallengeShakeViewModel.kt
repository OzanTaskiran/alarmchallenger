package com.example.alarmchallenger.viewmodel

import android.hardware.SensorEvent
import android.hardware.SensorManager
import android.util.DisplayMetrics
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ChallengeShakeViewModel : ViewModel() {

    private var acceleration = 0f
    private var currentAcceleration = SensorManager.GRAVITY_EARTH
    private var lastAcceleration = SensorManager.GRAVITY_EARTH


    val shakeValue: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>(0)
    }


    fun calculateShake(event: SensorEvent){
        val x = event.values[0]
        val y = event.values[1]
        val z = event.values[2]

        lastAcceleration = currentAcceleration
        currentAcceleration = kotlin.math.sqrt((x * x + y * y + z * z).toDouble()).toFloat()

        val delta: Float = currentAcceleration - lastAcceleration
        acceleration = acceleration * 0.9f + delta

        if (acceleration > 12) {
           shakeValue.value = shakeValue.value!! + 1
        }

    }

}