package com.example.sleeplogger.sleepDetailsScreen

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.sleeplogger.R
import com.example.sleeplogger.convertDateForDisplay
import com.example.sleeplogger.convertMinutesForDisplay
import com.example.sleeplogger.database.AppRepository
import com.example.sleeplogger.databinding.FragmentSleepDetailsBinding
import kotlin.math.floor

class SleepDetailsFragment : Fragment() {
    val dataSource = AppRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentSleepDetailsBinding>(
            inflater, R.layout.fragment_sleep_details, container, false
        )

        val arguments = arguments?.let { SleepDetailsFragmentArgs.fromBundle(it) }

        val viewModelFactory = arguments?.sleepId?.let {
            SleepDetailsViewModelFactory(it, dataSource)
        }

        val sleepDetailsViewModel = viewModelFactory?.let {
            ViewModelProvider(
                this, it
            ).get(SleepDetailsViewModel::class.java)
        }

        binding.sleepDetailsVM = sleepDetailsViewModel
        binding.lifecycleOwner = this

        sleepDetailsViewModel?.sleepLog?.observe(viewLifecycleOwner) {
            val sleepDurationDisplay: String =
                "${it?.let { sleepInfo -> floor(sleepInfo.sleepDuration).toInt() }} hours " +
                        "${it?.let { 
                                sleepInfo -> convertMinutesForDisplay(sleepInfo.sleepDuration)
                        }} minutes"

            binding.apply {
                if(it != null) {
                    dateTv.text = convertDateForDisplay(it.dateRecorded)
                    sleepDurationTv.text = sleepDurationDisplay
                    ratingTv.text = it.sleepQuality.toString()
                }
            }
        }

        binding.modifyButton.setOnClickListener {
            if (arguments != null) {
                view?.findNavController()?.navigate(
                    SleepDetailsFragmentDirections.actionSleepDetailsFragmentToLoggerFragment(
                        arguments.sleepId
                    )
                )
            }
        }

        binding.deleteButton.setOnClickListener {
            if (arguments != null) {
                deleteEvent(arguments.sleepId)
            }
        }

        return binding.root
    }

    private fun deleteEvent(sleepId: Int){
        var builder = AlertDialog.Builder(activity)

        builder.setTitle(getString(R.string.confirm_delete))
        builder.setMessage(getString(R.string.confirm_question))
        builder.setPositiveButton(getString(R.string.yes_button), DialogInterface.OnClickListener { dialog, _ ->
            Thread(Runnable {
                dataSource.deleteSleepInfo(sleepId)
            }).start()
            dialog.cancel()
            view?.findNavController()
                ?.navigate(SleepDetailsFragmentDirections.actionSleepDetailsFragmentToAllSleepInfoFragment())
        })
        builder.setNegativeButton(getString(R.string.no_button), DialogInterface.OnClickListener { dialog, _ ->
            dialog.cancel()
        })

        var alert = builder.create()
        alert.show()
    }
}