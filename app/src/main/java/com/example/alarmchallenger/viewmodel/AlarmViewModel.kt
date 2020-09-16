package com.example.alarmchallenger.viewmodel

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.alarmchallenger.data.model.Alarm
import com.example.alarmchallenger.receiver.AlarmReceiver
import com.example.alarmchallenger.repository.AlarmRepository
import com.example.alarmchallenger.util.formatNumberToString
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class AlarmViewModel(private val alarmRepository: AlarmRepository) : ViewModel() {


    val time: MutableLiveData<String> by lazy {
        MutableLiveData<String>("00:00")
    }

    private  var hour = 0
    private  var minute = 0
    var data: MutableLiveData<MutableList<Alarm>> = MutableLiveData(mutableListOf())
    private var highestId: Int? = 0

    fun init(){
        getAllAlarms()
    }


     fun getAllAlarms(){
        CoroutineScope(Dispatchers.Main).launch {
            data.value = alarmRepository.getAlarms()
        }
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    fun addAlarm(description: String, context: Context) {

        CoroutineScope(Dispatchers.Main).launch {
            val alarm = Alarm(hour, minute, description, true)
            alarmRepository.addAlarm(alarm)
            highestId = alarmRepository.getHighestId()
            alarm.id = highestId!!
            var newList = data.value
            newList!!.add(alarm)
            data.value =  newList


        }


        var alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0)
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 10000, pendingIntent )

    }

    fun deleteAlarm(id: Int, position: Int) {
        CoroutineScope(Dispatchers.Main).launch {
            alarmRepository.deleteAlarm(id)
            var newList = data.value
            newList!!.removeAt(position)
            data.value = newList
        }
    }

    fun deleteAll() {
        CoroutineScope(Dispatchers.Main).launch {
            alarmRepository.deleteAll()
        }
    }

    fun updateHour(hourParam: Int){
        hour = hourParam
        updateTime()
    }

    fun updateMinute(minuteParam: Int){
        minute = minuteParam
        updateTime()
    }

   private  fun updateTime(){
        var hourString = hour.formatNumberToString()
        var minuteString = minute.formatNumberToString()

        time.value = "$hourString:$minuteString"
    }

    fun updateActive(id: Int, isActive: Boolean){
        CoroutineScope(Dispatchers.Main).launch {
            alarmRepository.updateActive(id, isActive)
        }
    }


}