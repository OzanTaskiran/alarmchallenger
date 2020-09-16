package com.example.alarmchallenger.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.alarmchallenger.repository.AlarmRepository

@Suppress("UNCHECKED_CAST")
class AlarmViewModelFactory(
    private val repository: AlarmRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AlarmViewModel(repository) as T
    }
}