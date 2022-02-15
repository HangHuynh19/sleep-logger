package com.example.sleeplogger.allSleepInfoScreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sleeplogger.database.AppRepository
import com.example.sleeplogger.databinding.FragmentAllSleepInfoBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class AllSleepInfoFragment : Fragment() {
    private lateinit var _binding: FragmentAllSleepInfoBinding

    private val binding get() = _binding

    private lateinit var recyclerView: RecyclerView

    private val allSleepInfoViewModel: AllSleepInfoViewModel by activityViewModels {
        AllSleepInfoViewModelFactory(AppRepository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding =
            FragmentAllSleepInfoBinding.inflate(
                inflater, container, false
            )

        binding.addButton.setOnClickListener {
            view?.findNavController()?.navigate(
                AllSleepInfoFragmentDirections.actionAllSleepInfoFragmentToLoggerFragment(-1))
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = binding.sleepLogsRv
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val sleepInfoAdapter = SleepInfoAdapter {
            val action =
                AllSleepInfoFragmentDirections.actionAllSleepInfoFragmentToSleepDetailsFragment(it.sleepId)
            view.findNavController().navigate(action)
        }


        recyclerView.adapter = sleepInfoAdapter

        lifecycle.coroutineScope.launch {
            allSleepInfoViewModel.fullSleepLogs().collect {
                sleepInfoAdapter.submitList(it)
            }
        }
    }
}

