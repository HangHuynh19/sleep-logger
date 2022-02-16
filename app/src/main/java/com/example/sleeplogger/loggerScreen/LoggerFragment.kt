package com.example.sleeplogger.loggerScreen

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.sleeplogger.R
import com.example.sleeplogger.convertMinutesForDisplay
import com.example.sleeplogger.convertTimeInputForDatabase
import com.example.sleeplogger.database.AppRepository
import com.example.sleeplogger.databinding.FragmentLoggerBinding
import com.example.sleeplogger.modifyDateInputForDatabase
import com.example.sleeplogger.sleepDetailsScreen.SleepDetailsFragmentArgs
import kotlin.math.floor
import kotlin.math.roundToInt

class LoggerFragment : Fragment() {

    private lateinit var binding: FragmentLoggerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_logger, container, false
        )

        val arguments = arguments?.let { SleepDetailsFragmentArgs.fromBundle(it) }

        val dataSource = AppRepository

        val viewModelFactory = arguments?.sleepId?.let {
            LoggerViewModelFactory(it, dataSource)
        }

        val loggerViewModel =
            viewModelFactory?.let {
                ViewModelProvider(this, it)[LoggerViewModel::class.java]
            }

        binding.loggerViewModel = loggerViewModel
        binding.lifecycleOwner = this

        setUpListeners()

        if (arguments == null || arguments.sleepId == -1) {
            binding.apply {
                hourInput.setText("0")
                minuteInput.setText("0")
                ratingBar.rating = 0F
            }
        } else {
            loggerViewModel?.sleepLog?.observe(viewLifecycleOwner) {
                val minutes =
                    it?.let { sleepInfo -> convertMinutesForDisplay(sleepInfo.sleepDuration) }

                binding.apply {
                    if (it != null) {
                        hourInput.setText(floor(it.sleepDuration).toInt().toString())
                        minuteInput.setText(minutes?.toInt().toString())
                        ratingBar.rating = it.sleepQuality.toFloat() ?: 0F
                    }
                }
            }
        }

        binding.sendButton.setOnClickListener {

            if (isValid()) {
                val dateSelected = modifyDateInputForDatabase(
                    binding.datePicker.dayOfMonth,
                    binding.datePicker.month + 1, binding.datePicker.year
                )

                val timeSelected =
                    convertTimeInputForDatabase(
                        binding.hourInput.text.toString(),
                        binding.minuteInput.text.toString()
                    )

                loggerViewModel?.apply {
                    onSendButtonClicked(
                        dateSelected,
                        timeSelected,
                        binding.ratingBar.rating.toInt(),
                        System.currentTimeMillis()
                    )

                    Toast.makeText(context, "Log submitted", Toast.LENGTH_SHORT).show()

                    view?.findNavController()?.navigate(
                        LoggerFragmentDirections.actionLoggerFragmentToAllSleepInfoFragment()
                    )
                }
            }
        }

        return binding.root
    }

    private fun isValid(): Boolean =
        validateHourInput() && validateMinuteInput() && validateRatingBar()

    private fun setUpListeners() {
        binding.hourInput.addTextChangedListener(TextFieldValidation(binding.hourInput))
        binding.minuteInput.addTextChangedListener(TextFieldValidation(binding.minuteInput))
    }

    private fun validateHourInput(): Boolean {
        if (binding.hourInput.text.trim().isEmpty()) {
            binding.hourInput.error = "Require field"
            binding.hourInput.requestFocus()
            return false
        }
        if (binding.hourInput.text.toString().toInt() > 12) {
            binding.hourInput.error = "The maximum sleep duration is 12 hours"
            binding.hourInput.requestFocus()
            return false
        }

        return true
    }

    private fun validateMinuteInput(): Boolean {
        if (binding.minuteInput.text.trim().isEmpty()) {
            binding.minuteInput.error = "Require field"
            return false
        }
        if (binding.hourInput.text.toString().toInt() == 12
            && binding.minuteInput.text.toString().toInt() > 0
        ) {
            binding.minuteInput.error = "The maximum sleep duration is 12 hours"
            return false
        }
        if (binding.minuteInput.text.toString().toInt() > 59) {
            binding.minuteInput.error = "Invalid input"
            return false
        }

        return true
    }

    private fun validateRatingBar(): Boolean {
        if (binding.ratingBar.rating.toInt() < 1) {
            Toast.makeText(context, "Please give at least 1 star", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }

    inner class TextFieldValidation(private val view: View) : TextWatcher {
        override fun afterTextChanged(s: Editable?) {}
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            when (view.id) {
                R.id.hourInput -> {
                    validateHourInput()
                }
                R.id.minuteInput -> {
                    validateMinuteInput()
                }
            }
        }
    }
}