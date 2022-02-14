package com.example.sleeplogger.allSleepInfoScreen

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sleeplogger.R
import com.example.sleeplogger.database.AppRepository
import com.example.sleeplogger.databinding.FragmentAllSleepInfoBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class AllSleepInfoFragment : Fragment() {
    private lateinit var _binding: FragmentAllSleepInfoBinding

    private val binding get() = _binding

    private lateinit var recyclerView: RecyclerView

    private val viewModel: AllSleepInfoViewModel by activityViewModels {
        AllSleepInfoViewModelFactory(AppRepository)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding =
            FragmentAllSleepInfoBinding.inflate(
                inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = binding.sleepLogsRv
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val sleepInfoAdapter = SleepInfoAdapter()
        recyclerView.adapter = sleepInfoAdapter
        lifecycle.coroutineScope.launch {
            viewModel.fullSleepLogs().collect() {
                sleepInfoAdapter.submitList(it)
            }
        }
    }
}