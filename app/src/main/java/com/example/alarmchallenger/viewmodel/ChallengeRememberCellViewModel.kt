package com.example.alarmchallenger.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ChallengeRememberCellViewModel : ViewModel() {

    var fieldList: MutableLiveData<MutableList<Int>> = MutableLiveData(mutableListOf())
    var inputCounter = 0
    var acceptInput: MutableLiveData<Boolean> = MutableLiveData()


     fun createRandomField(){
        var value = (0..5).shuffled().first()
        var newList = fieldList.value
         while(newList!!.contains(value)){
             value = (0..5).shuffled().first()
         }
        newList!!.add(value)
        fieldList.value = newList
    }


    fun checkSelectedField(field: Int){
        if(field == fieldList.value!![inputCounter]){
            inputCounter++
            createRandomField()
            acceptInput.value = true
        }
        else{
            inputCounter = 0
            acceptInput.value = false
        }
    }



}