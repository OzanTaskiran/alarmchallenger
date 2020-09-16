package com.example.alarmchallenger.adapter
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.alarmchallenger.R
import com.example.alarmchallenger.data.model.Alarm
import com.example.alarmchallenger.util.formatNumberToString
import com.example.alarmchallenger.viewmodel.AlarmViewModel
import kotlinx.android.synthetic.main.alarmlist_item.view.*

class AlarmAdapter(var dataList: List<Alarm>, private val viewModel: AlarmViewModel) : RecyclerView.Adapter<AlarmAdapter.AlarmViewHolder>() {


    fun getNoteAt(position: Int) =  dataList[position]

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlarmViewHolder {
        var itemView = LayoutInflater.from(parent.context).inflate(R.layout.alarmlist_item, parent, false)
        return AlarmViewHolder(
            itemView,
            viewModel
        )
    }

    override fun getItemCount() : Int = dataList.size

    override fun onBindViewHolder(holder: AlarmViewHolder, position: Int) {
        holder.bind(dataList[position])
        holder.isActive.setOnCheckedChangeListener { switch, isChecked ->
            if(switch.isPressed){
                viewModel.updateActive(dataList[position].id, isChecked)
                dataList[position].isActive = isChecked
            }

        }



    }


    class AlarmViewHolder(itemView: View, var viewModel: AlarmViewModel) : RecyclerView.ViewHolder(itemView){
        private val time: TextView = itemView.tvTime
        private val description: TextView = itemView.tvDescription
          val isActive: Switch = itemView.swIsActive




        fun bind(alarm: Alarm){
            time.text = alarm.hour.formatNumberToString() + ":" + alarm.minute.formatNumberToString()
            description.text = alarm.description
            isActive.isChecked = alarm.isActive
        }
    }
}