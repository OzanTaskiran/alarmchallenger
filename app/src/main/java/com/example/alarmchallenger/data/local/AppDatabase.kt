package com.example.alarmchallenger.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.alarmchallenger.data.model.Alarm

@Database(
    entities = [Alarm::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun alarmDao() : AlarmDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null
        private val LOCK = Any()

          fun instance(context: Context) = instance ?: synchronized(LOCK){
            instance ?: createDatabase(context).also { instance = it }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
            AppDatabase::class.java,
            "AppDatabase.db").build()
    }
}