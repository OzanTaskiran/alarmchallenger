package com.example.alarmchallenger.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.alarmchallenger.R
import com.google.android.material.card.MaterialCardView
import kotlinx.android.synthetic.main.challenge_item.view.*
import java.io.File

class ChallengeAdapter(var list: List<File>) : RecyclerView.Adapter<ChallengeAdapter.ChallengeViewHolder>() {


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChallengeViewHolder {
            return ChallengeViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.challenge_item, parent, false))

        }

        override fun getItemCount(): Int {
           return 10
        }

        override fun onBindViewHolder(holder: ChallengeViewHolder, position: Int) {
            holder.bind()
        }

        class ChallengeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var cardView: MaterialCardView = itemView.cvChallenge

            fun bind(){
                cardView.setOnLongClickListener {
                    Log.d("TEST", "LCHECKED")
                    cardView.isChecked = cardView.isChecked
                    true
                }
            }
        }
}