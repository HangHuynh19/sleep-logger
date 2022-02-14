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
import com.example.sleeplogger.R
import com.example.sleeplogger.database.AppRepository
import com.example.sleeplogger.databinding.FragmentLoggerBinding

class LoggerFragment : Fragment() {

    lateinit var binding: FragmentLoggerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_logger, container, false)

        val dataSource = AppRepository

        val viewModelFactory = LoggerViewModelFactory(dataSource)

        val loggerViewModel =
            viewModelFactory.let {
                ViewModelProvider(this, it)[LoggerViewModel::class.java]
            }

        binding.loggerViewModel = loggerViewModel
        binding.lifecycleOwner = this

        setUpListeners()

        binding.sendButton.setOnClickListener {
            val dateSelected = "${binding.datePicker.dayOfMonth}" +
                    "-${binding.datePicker.month + 1}-${binding.datePicker.year}"


            val sleepInHours = binding.hourInput.text.toString().toInt()
            val sleepInMinutes = binding.minuteInput.text.toString().toInt()

            if (isValid()) {
                loggerViewModel.apply {
                    onSendButtonClicked(
                        dateSelected,
                        1.00 * (sleepInHours + sleepInMinutes / 60),
                        binding.ratingBar.rating.toInt(),
                        System.currentTimeMillis()
                    )

                    Toast.makeText(context, "Log submitted", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(context, "Invalid input", Toast.LENGTH_SHORT).show()
            }

            binding.hourInput.text = null
            binding.minuteInput.text = null
            binding.ratingBar.rating = 0F
        }

        return binding.root
    }

    private fun isValid() : Boolean =
        validateHourInput() && validateMinuteInput() && validateRatingBar()

    private fun setUpListeners() {
        binding.hourInput.addTextChangedListener(TextFieldValidation(binding.hourInput))
        binding.minuteInput.addTextChangedListener(TextFieldValidation(binding.minuteInput))
    }

    private fun validateHourInput() : Boolean {
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

    private fun validateMinuteInput() : Boolean {
        if (binding.minuteInput.text.trim().isEmpty()) {
            binding.minuteInput.error = "Require field"
            return false
        }
        if (binding.hourInput.text.toString().toInt() == 12
                    && binding.minuteInput.text.toString().toInt() > 0) {
            binding.minuteInput.error = "The maximum sleep duration is 12 hours"
            return false
        }
        if (binding.minuteInput.text.toString().toInt() > 59) {
            binding.minuteInput.error = "Invalid input"
            return false
        }

        return true
    }

    private fun validateRatingBar() : Boolean {
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
            // checking ids of each text field and applying functions accordingly.
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