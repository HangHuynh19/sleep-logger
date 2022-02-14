package com.example.sleeplogger.allSleepInfoScreen

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.sleeplogger.database.SleepInfo
import com.example.sleeplogger.databinding.SleepInfoItemLayoutBinding

class SleepInfoAdapter : ListAdapter<SleepInfo, SleepInfoAdapter.ViewHolder>(SleepInfoDiffCallBack()) {
    class ViewHolder private constructor(private val binding: SleepInfoItemLayoutBinding)
        : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SleepInfo) {
            binding.dateTv.text = item.dateRecorded
            binding.sleepDurationTv.text = item.sleepDuration.toString()
            binding.ratingTv.text = item.sleepQuality.toString()
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup) : ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = SleepInfoItemLayoutBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.i("aaa", "recyclerview inflated")
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
        Log.i("aaa", "recyclerview inflated")
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