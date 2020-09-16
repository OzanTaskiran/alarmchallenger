package com.example.alarmchallenger.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Alarm(var hour: Int, var minute: Int,var description: String, var isActive: Boolean) {
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0
}