package com.example.swiftprint.fragment.hotel.fragment

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import android.content.SharedPreferences

class HotelViewModel(application: Application) : AndroidViewModel(application) {
    private val sharedPreferences: SharedPreferences = application.getSharedPreferences("HotelPrefs", Context.MODE_PRIVATE)
    private val gson = Gson()
    private val COUNTER_KEY = "tiketCounter"
    private val HOTEL_LIST_KEY = "hotellist"

    private val _counter = MutableLiveData<Long>()
    val counter: LiveData<Long> get() = _counter

    private val _hotels = MutableLiveData<MutableList<String>>()
    val hotels: LiveData<MutableList<String>> get() = _hotels

    init {
        loadCounter()
        loadHotels()
    }

    private fun loadCounter() {
        _counter.value = sharedPreferences.getLong(COUNTER_KEY, 642149621)
    }

    fun incrementCounter() {
        _counter.value = (_counter.value ?: 642149621) + 1
        sharedPreferences.edit().putLong(COUNTER_KEY, _counter.value!!).apply()
    }

    fun addHotel(hotel: String) {
        val newList = _hotels.value?.toMutableList() ?: mutableListOf()
        if (!newList.contains(hotel)) {
            newList.add(hotel)
            _hotels.value = newList
            saveHotels()
        }
    }

    fun removeHotel(hotel: String) {
        val newList = _hotels.value?.toMutableList() ?: mutableListOf()
        if (newList.remove(hotel)) {
            _hotels.value = newList
            saveHotels()
        }
    }

    private fun saveHotels() {
        sharedPreferences.edit().putString(HOTEL_LIST_KEY, gson.toJson(_hotels.value)).apply()
    }

    private fun loadHotels() {
        val json = sharedPreferences.getString(HOTEL_LIST_KEY, null)
        val type = object : TypeToken<MutableList<String>>() {}.type
        _hotels.value = gson.fromJson(json, type) ?: mutableListOf()
    }
}