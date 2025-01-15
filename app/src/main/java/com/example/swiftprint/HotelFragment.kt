package com.example.swiftprint

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.swiftprint.databinding.FragmentHotelBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.Calendar

class HotelFragment : Fragment() {
    private val COUNTERKEY = "tiketCounter"
    lateinit var calendar: Calendar
    var Counter: Long = 642149621
    private lateinit var binding: FragmentHotelBinding
    private lateinit var adapter: ArrayAdapter<String>
    private val sharedPreferences: SharedPreferences by lazy {
        requireContext().getSharedPreferences("HotelPrefs", Context.MODE_PRIVATE)
    }
    private val gson = Gson()
    private var hotelList = mutableListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHotelBinding.inflate(inflater, container, false)
        loadCounter()
        loadHotels()
        initView()
        return binding.root
    }



    private fun initView() {
        calendar = Calendar.getInstance()
        binding.editButton.setOnClickListener {
            binding.editHotel.isVisible = true
            binding.hoteltextfield.isVisible = true
            binding.fragmentContainer.isVisible = false

            binding.hotelEditText.requestFocus()
            val imm =
                requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(binding.hotelEditText, InputMethodManager.SHOW_IMPLICIT)
        }
        adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, hotelList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinner.adapter = adapter

        binding.addButton.setOnClickListener { handleAddHotel() }
        binding.addButtontv.setOnClickListener { handleAddHotel() }
        binding.removeButton.setOnClickListener { handledeleteHotel() }
        binding.removeButtontv.setOnClickListener { handledeleteHotel() }
        binding.selectDateGo.setOnClickListener { showGoDateBicker() }
        binding.selectDateBack.setOnClickListener { showBackDateBicker() }
        binding.done.setOnClickListener { NavigateToPrintTextActivity() }
    }

    private fun loadCounter() {
        Counter = sharedPreferences.getLong(COUNTERKEY, 642149621)
        android.util.Log.d("HotelFragment", "Counter loaded: $Counter")
    }

    private fun saveCounter() {
        sharedPreferences.edit().putLong(COUNTERKEY, Counter).apply()
        android.util.Log.d("HotelFragment", "Counter saved: $Counter")
    }

    private fun NavigateToPrintTextActivity() {
        Counter += 1
        saveCounter()
        Toast.makeText(requireContext(), "Counter updated: $Counter", Toast.LENGTH_SHORT).show()
        android.util.Log.d("HotelFragment", "Counter after increment: $Counter")

        val selectedHotel = binding.spinner.selectedItem?.toString() ?: ""
        val name = binding.name.text.toString()
        val goDate = binding.selectDateGo.text.toString()
        val backDate = binding.selectDateBack.text.toString()

        val intent = Intent(requireContext(), TiketDetailsActivity::class.java).apply {
            putExtra("selected_hotel", selectedHotel)
            putExtra("tiketCounter", Counter.toString())
            putExtra("go_date", goDate)
            putExtra("back_date", backDate)
        }
        startActivity(intent)
    }


    private fun showGoDateBicker() {
        val picker = DatePickerDialog(
            requireContext(),
            { _, year, month, day ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, day)
                binding.selectDateGo.text = "$year/${month + 1}/$day"
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        picker.show()
    }

    private fun showBackDateBicker() {
        val picker = DatePickerDialog(
            requireContext(),
            { _, year, month, day ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, day)
                binding.selectDateBack.text = "$year/${month + 1}/$day"
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        picker.show()
    }

    private fun handledeleteHotel() {
        val hotelName = binding.hotelEditText.text.toString()
        if (hotelName.isNotEmpty()) {
            removeHotel(hotelName)
            binding.hotelEditText.text.clear()
            binding.editHotel.isVisible = false
            binding.hoteltextfield.isVisible = false
            binding.fragmentContainer.isVisible = true
            Toast.makeText(requireContext(), "تم حذف الفندق بنجاح", Toast.LENGTH_SHORT).show()
        }
    }

    private fun handleAddHotel() {
        val hotelName = binding.hotelEditText.text.toString()
        if (hotelName.isNotEmpty()) {
            addHotel(hotelName)
            binding.hotelEditText.text.clear()
            binding.editHotel.isVisible = false
            binding.hoteltextfield.isVisible = false
            binding.fragmentContainer.isVisible = true
            Toast.makeText(requireContext(), "تم إضافة الفندق بنجاح", Toast.LENGTH_SHORT).show()
        }
    }

    private fun addHotel(newHotel: String) {
        if (!hotelList.contains(newHotel)) {
            hotelList.add(newHotel)
            adapter.notifyDataSetChanged()
            saveHotels()
        }
    }

    private fun removeHotel(hotelToRemove: String) {
        if (hotelList.contains(hotelToRemove)) {
            hotelList.remove(hotelToRemove)
            adapter.notifyDataSetChanged()
            saveHotels()
        }
    }

    private fun saveHotels() {
        val json = gson.toJson(hotelList)
        sharedPreferences.edit().putString("hotellist", json).apply()
    }

    private fun loadHotels() {
        val json = sharedPreferences.getString("hotellist", null)
        if (json != null) {
            val type = object : TypeToken<MutableList<String>>() {}.type
            hotelList = gson.fromJson(json, type)
        } else {
            hotelList = mutableListOf()
        }
    }
}
