package com.example.sleeplogger.allSleepInfoScreen

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sleeplogger.R
import com.example.sleeplogger.database.AppRepository
import com.example.sleeplogger.databinding.FragmentAllSleepInfoBinding

class AllSleepInfoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding =
            DataBindingUtil.inflate<FragmentAllSleepInfoBinding>(
                inflater, R.layout.fragment_all_sleep_info, container, false)

        val dataSource = AppRepository
        val allSleepInfoViewModel = AllSleepInfoViewModel(dataSource)

        binding.allSleepInfoVM = allSleepInfoViewModel

        val manager = LinearLayoutManager(this.activity)
        binding.sleepLogsRv.layoutManager = manager

        val adapter = SleepInfoAdapter()
        binding.sleepLogsRv.adapter = adapter

        binding.lifecycleOwner = this

        //allSleepInfoViewModel.allSleepInfo.observe(viewLifecycleOwner, {
        //    adapter.submitList(it)
        //})

        return binding.root
    }
}