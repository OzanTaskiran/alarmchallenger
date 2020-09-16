package com.example.alarmchallenger.data.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import com.example.alarmchallenger.data.model.Alarm

@Dao
interface AlarmDao {

    @Query("SELECT * FROM Alarm")
     suspend fun getAlarms() : MutableList<Alarm>

    @Query("DELETE FROM Alarm WHERE id = :id")
     suspend fun deleteAlarm(id: Int)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addAlarm(alarm: Alarm)

    @Query("UPDATE Alarm SET isActive = :isActive WHERE id = :id")
    suspend fun updateActive(id: Int, isActive: Boolean)

    @Query("SELECT MAX(id) FROM Alarm")
    suspend fun getHighestId() : Int

    @Query("DELETE FROM Alarm")
    suspend fun deleteAll()
}