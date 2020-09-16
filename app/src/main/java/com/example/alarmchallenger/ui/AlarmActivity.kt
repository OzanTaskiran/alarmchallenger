package com.example.alarmchallenger.ui

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.alarmchallenger.adapter.AlarmAdapter
import com.example.alarmchallenger.R
import com.example.alarmchallenger.data.local.AppDatabase
import com.example.alarmchallenger.receiver.AlarmReceiver
import com.example.alarmchallenger.repository.AlarmRepository
import com.example.alarmchallenger.viewmodel.AlarmViewModel
import com.example.alarmchallenger.viewmodel.AlarmViewModelFactory
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import kotlinx.android.synthetic.main.activity_alarmlist.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class AlarmActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    lateinit var recyclerViewAdapter: AlarmAdapter
    lateinit var fabAdd : ExtendedFloatingActionButton
    private val bottomSheetFragment: AlarmBottomSheetFragment = AlarmBottomSheetFragment()
    lateinit  var viewModel : AlarmViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarmlist)

        viewModel = ViewModelProvider(this, AlarmViewModelFactory(AlarmRepository(AppDatabase.instance(this)))).get(AlarmViewModel::class.java)


        recyclerViewAdapter = AlarmAdapter(viewModel.data?.value!!, viewModel)
        recyclerView = rvAlarmList
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = recyclerViewAdapter
        recyclerView.setHasFixedSize(true)



        val itemTouchHelperCallback =
            object :
                ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {

                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    viewModel.deleteAlarm(recyclerViewAdapter.getNoteAt(viewHolder.adapterPosition).id, viewHolder.adapterPosition)
                    Toast.makeText(
                        this@AlarmActivity,
                        "Gelöscht mit der ID " + recyclerViewAdapter.getNoteAt(viewHolder.adapterPosition).id.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)

        viewModel.init()




        viewModel.data.observe(this, Observer {
            recyclerViewAdapter.dataList = it
            recyclerView.scrollToPosition(it.size-1)
            recyclerViewAdapter.notifyDataSetChanged()
        })


        fabAdd = fabAddAlarm
        fabAdd.setOnClickListener {
            if(!bottomSheetFragment.isAdded){
                bottomSheetFragment.show(supportFragmentManager, "addAlarmBottomSheet")
            }

        }

    }

}

