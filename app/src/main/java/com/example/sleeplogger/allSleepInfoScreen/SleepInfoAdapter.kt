package com.example.sleeplogger.allSleepInfoScreen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.sleeplogger.database.SleepInfo
import com.example.sleeplogger.databinding.SleepInfoItemLayoutBinding

class SleepInfoAdapter : ListAdapter<SleepInfo, SleepInfoAdapter.SleepInfoViewHolder>(SleepInfoDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SleepInfoViewHolder {

        return SleepInfoViewHolder(
            SleepInfoItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: SleepInfoViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class SleepInfoViewHolder(private var binding: SleepInfoItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
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