package com.example.alarmchallenger.util

import android.view.View
import android.view.ViewGroup.MarginLayoutParams


fun Int.formatNumberToString() : String {
    if (this!! < 10) {
        return "0${this.toString()}"
    }
    return this.toString()
}

