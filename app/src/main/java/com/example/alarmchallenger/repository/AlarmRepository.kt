package com.example.alarmchallenger.repository

import androidx.lifecycle.LiveData
import com.example.alarmchallenger.data.local.AlarmDao
import com.example.alarmchallenger.data.local.AppDatabase
import com.example.alarmchallenger.data.model.Alarm

class AlarmRepository(private val database: AppDatabase) {

    companion object {
        @Volatile
        private var instance: AlarmRepository? = null
        private val LOCK = Any()

        operator fun invoke(database: AppDatabase) = instance ?: synchronized(LOCK) {
            instance ?: AlarmRepository(database).also { instance = it }
        }
    }

     suspend fun getAlarms() = database.alarmDao().getAlarms()
     suspend fun addAlarm(alarm: Alarm) = database.alarmDao().addAlarm(alarm)
     suspend fun deleteAlarm(id: Int) = database.alarmDao().deleteAlarm(id)
     suspend fun updateActive(id: Int, isActive: Boolean) = database.alarmDao().updateActive(id, isActive)
     suspend fun getHighestId() = database.alarmDao().getHighestId()
     suspend fun deleteAll() = database.alarmDao().deleteAll()

}