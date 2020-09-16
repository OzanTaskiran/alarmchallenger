package com.example.alarmchallenger.ui

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.alarmchallenger.R
import com.example.alarmchallenger.adapter.ChallengeAdapter
import com.example.alarmchallenger.data.local.AppDatabase
import com.example.alarmchallenger.repository.AlarmRepository
import com.example.alarmchallenger.viewmodel.AlarmViewModel
import com.example.alarmchallenger.viewmodel.AlarmViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.bottomsheet_addalarm.view.*


class AlarmBottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var  viewModel: AlarmViewModel
    private lateinit var challengeAdapter: ChallengeAdapter
    private lateinit var  recyclerView: RecyclerView


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var view =  inflater.inflate(R.layout.bottomsheet_addalarm, container, false)

        recyclerView = view.rvChallenge
        challengeAdapter = ChallengeAdapter(listOf())
        recyclerView.adapter = challengeAdapter
        recyclerView.layoutManager = LinearLayoutManager(activity!!, LinearLayoutManager.HORIZONTAL, true)





        view.fabAddAlarm.setOnClickListener {
            viewModel.addAlarm(view.etDescription.text.toString(), activity!!)
            view.etDescription.setText("")
            dismiss()
        }


        viewModel = ViewModelProvider(activity!!, AlarmViewModelFactory(AlarmRepository(AppDatabase.instance(activity!!.applicationContext)))).get(AlarmViewModel::class.java)

        viewModel.time.observe(viewLifecycleOwner, Observer {
           view.tvselectedTime.text = it
        })

        dialog!!.setOnShowListener { dialog ->
            val d = dialog as BottomSheetDialog
            val bottomSheet = d.findViewById<View>(R.id.design_bottom_sheet) as FrameLayout
            val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        }


        view.slHour.addOnChangeListener { slider, value, fromUser ->
            var vibrator =  activity!!.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            vibrator.vibrate(VibrationEffect.createOneShot(30, 4))
            viewModel.updateHour(value.toInt())
        }

        view.slMinute.addOnChangeListener { slider, value, fromUser ->
            var vibrator =  activity!!.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            vibrator.vibrate(VibrationEffect.createOneShot(30, 1))
            viewModel.updateMinute(value.toInt())
        }

        return view
    }


}