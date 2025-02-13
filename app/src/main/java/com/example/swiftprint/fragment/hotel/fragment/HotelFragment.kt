package com.example.swiftprint.fragment.hotel.fragment
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.swiftprint.databinding.FragmentHotelBinding
import com.example.swiftprint.fragment.hotel.tiket.TiketDetailsActivity
import java.util.Calendar

class HotelFragment : Fragment() {
    private lateinit var binding: FragmentHotelBinding
    private lateinit var adapter: ArrayAdapter<String>
    private val viewModel: HotelViewModel by viewModels()
    private lateinit var calendar: Calendar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHotelBinding.inflate(inflater, container, false)
        setupUI()
        observeViewModel()
        return binding.root
    }

    private fun setupUI() {
        calendar = Calendar.getInstance()
        setupSpinner()
        setupButtons()
    }

    private fun setupSpinner() {
        adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinner.adapter = adapter
    }

    private fun setupButtons() {
        binding.editButton.setOnClickListener { showHotelEditField() }
        binding.addButton.setOnClickListener { handleAddHotel() }
        binding.addButtontv.setOnClickListener { handleAddHotel() }
        binding.removeButton.setOnClickListener { handleRemoveHotel() }
        binding.removeButtontv.setOnClickListener { handleRemoveHotel() }
        binding.selectDateGo.setOnClickListener { showDatePicker(true) }
        binding.selectDateBack.setOnClickListener { showDatePicker(false) }
        binding.done.setOnClickListener { navigateToTiketDetails() }
    }

    private fun observeViewModel() {
        viewModel.hotels.observe(viewLifecycleOwner) { hotels ->
            adapter.clear()
            adapter.addAll(hotels)
        }
    }

    private fun showHotelEditField() {
        binding.editHotel.isVisible = true
        binding.hoteltextfield.isVisible = true
        binding.fragmentContainer.isVisible = false
        binding.hotelEditText.requestFocus()
        showKeyboard()
    }

    private fun showKeyboard() {
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(binding.hotelEditText, InputMethodManager.SHOW_IMPLICIT)
    }

    private fun handleAddHotel() {
        val hotelName = binding.hotelEditText.text.toString()
        if (hotelName.isNotEmpty()) {
            viewModel.addHotel(hotelName)
            resetEditFields()
            showToast("تم إضافة الفندق بنجاح")
        }
    }

    private fun handleRemoveHotel() {
        val hotelName = binding.hotelEditText.text.toString()
        if (hotelName.isNotEmpty()) {
            viewModel.removeHotel(hotelName)
            resetEditFields()
            showToast("تم حذف الفندق بنجاح")
        }
    }

    private fun resetEditFields() {
        binding.hotelEditText.text.clear()
        binding.editHotel.isVisible = false
        binding.hoteltextfield.isVisible = false
        binding.fragmentContainer.isVisible = true
    }

    private fun showDatePicker(isDeparture: Boolean) {
        DatePickerDialog(
            requireContext(),
            { _, year, month, day ->
                val dateText = "$year/${month + 1}/$day"
                if (isDeparture) binding.selectDateGo.text = dateText
                else binding.selectDateBack.text = dateText
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    private fun navigateToTiketDetails() {
        viewModel.incrementCounter()
        showToast("تم تحديث العداد: ${viewModel.counter.value}")

        Intent(requireContext(), TiketDetailsActivity::class.java).apply {
            putExtra("selected_hotel", binding.spinner.selectedItem?.toString())
            putExtra("tiketCounter", viewModel.counter.value.toString())
            putExtra("go_date", binding.selectDateGo.text.toString())
            putExtra("back_date", binding.selectDateBack.text.toString())
        }.also { startActivity(it) }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}