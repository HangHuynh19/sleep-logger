package com.example.sleeplogger.allSleepInfoScreen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.sleeplogger.database.SleepInfo
import com.example.sleeplogger.databinding.SleepInfoItemLayoutBinding

class SleepInfoAdapter(private val onItemClicked: (SleepInfo) -> Unit) :
    ListAdapter<SleepInfo, SleepInfoAdapter.AllSleepInfoViewHolder>(SleepInfoDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllSleepInfoViewHolder {

        val viewHolder = AllSleepInfoViewHolder(
            SleepInfoItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.adapterPosition
            onItemClicked(getItem(position))
        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: AllSleepInfoViewHolder, position: Int) {
        holder.bind(getItem(position))

    }

    inner class AllSleepInfoViewHolder (private var binding: SleepInfoItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: SleepInfo) {
            binding.dateTv.text = item.dateRecorded
            binding.sleepDurationTv.text = item.sleepDuration.toString()
            binding.ratingTv.text = item.sleepQuality.toString()
        }
    }
}

class SleepInfoDiffCallBack : DiffUtil.ItemCallback<SleepInfo>() {
    override fun areItemsTheSame(oldItem: SleepInfo, newItem: SleepInfo): Boolean {
        return oldItem.sleepId == newItem.sleepId
    }

    override fun areContentsTheSame(oldItem: SleepInfo, newItem: SleepInfo): Boolean {
        return oldItem == newItem
    }
}

