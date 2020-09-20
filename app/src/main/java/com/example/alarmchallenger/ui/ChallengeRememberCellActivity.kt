package com.example.alarmchallenger.ui

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.alarmchallenger.R
import com.example.alarmchallenger.data.model.RememberCell
import com.example.alarmchallenger.viewmodel.ChallengeRememberCellViewModel
import kotlinx.android.synthetic.main.activity_challenge_remember_cell.*
import kotlinx.coroutines.*


class ChallengeRememberCellActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var  challengeRememberCellViewModel: ChallengeRememberCellViewModel
    private var fields: MutableList<RememberCell> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_challenge_remember_cell)


        fields.add(RememberCell(fieldTopLeft,false))
        fields.add(RememberCell(fieldTopRight,false))
        fields.add(RememberCell(fieldMiddleLeft,false))
        fields.add(RememberCell(fieldMiddleRight,false))
        fields.add(RememberCell(fieldBottomLeft,false))
        fields.add(RememberCell(fieldBottomRight,false))

        for(i in fields){
            i.cell.setOnClickListener(this)
        }

        challengeRememberCellViewModel = ViewModelProvider(this).get(ChallengeRememberCellViewModel::class.java)


        challengeRememberCellViewModel.acceptInput.observe(this, Observer {
            var listener = if(it) this else null
            for(i in fields){
                i.cell.setOnClickListener(listener)
            }
        })


        challengeRememberCellViewModel.fieldList.observe(this, Observer {
            for( i in 0 until it.size){
                CoroutineScope(Dispatchers.Default).launch {
                    withContext(Dispatchers.Main){
                        fields[it[i]].cell.setBackgroundColor(resources.getColor(R.color.colorPrimary))
                        fields[it[i]].isActive = true
                    }
                    delay(1000)
                    fields[it[i]].cell.setBackgroundColor(resources.getColor(R.color.cardColor) )
                }

            }
        })

        generateFields()





    }

    private fun generateFields(){
        CoroutineScope(Dispatchers.Main).launch {
            challengeRememberCellViewModel.createRandomField()
        }
    }

    override fun onClick(v: View?) {

        when(v!!.id){
            R.id.fieldTopLeft -> {
                fields[0].cell.setBackgroundColor(resources.getColor(R.color.colorAccent))
                checkSelectedField(0)
            }

            R.id.fieldTopRight -> {
                fields[1].cell.setBackgroundColor(resources.getColor(R.color.colorAccent))
                checkSelectedField(1)
            }

            R.id.fieldMiddleLeft -> {
                fields[2].cell.setBackgroundColor(resources.getColor(R.color.colorAccent))
                checkSelectedField(2)
            }

            R.id.fieldMiddleRight -> {
                fields[3].cell.setBackgroundColor(resources.getColor(R.color.colorAccent))
                checkSelectedField(3)
            }

            R.id.fieldBottomLeft -> {
                fields[4].cell.setBackgroundColor(resources.getColor(R.color.colorAccent))
                checkSelectedField(4)
            }

            R.id.fieldBottomRight -> {
                fields[5].cell.setBackgroundColor(resources.getColor(R.color.colorAccent))
                checkSelectedField(5)
            }
        }
    }

    private fun checkSelectedField(field: Int) {
        challengeRememberCellViewModel.checkSelectedField(field)
    }
}

