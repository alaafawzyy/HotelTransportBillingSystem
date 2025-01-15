package com.example.swiftprint.fragment.operation

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import com.example.swiftprint.database.model.AppDatabase
import com.example.swiftprint.databinding.FragmentOperationDataFormBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class operationDataFormFragment : Fragment() {
    lateinit var binding: FragmentOperationDataFormBinding
    lateinit var sharedPreferences: SharedPreferences
    lateinit var calendar: Calendar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentOperationDataFormBinding.inflate(inflater)
        calendar = Calendar.getInstance()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreferences = requireContext().getSharedPreferences("user_preferences", Context.MODE_PRIVATE)

        loadSavedData()
        setCurrentDate()
        initializeCarSpinner()
        initializeDriverSpinner()
        binding.dateTv.setOnClickListener{ showDateBicker() }
        binding.arriveTimeTv.setOnClickListener{showTimePicker()}
        binding.addPerson1.setOnClickListener{HandlePersonOneVisibility()}
        binding.addPerson2.setOnClickListener{HandlePersonTwoVisibility()}
        binding.addPerson3.setOnClickListener{HandlePersonThreeVisibility()}

        binding.addPerson4.setOnClickListener{HandlePersonFourVisibility()}
        binding.addPerson5.setOnClickListener{HandlePersonFiveVisibility()}
        binding.addPerson6.setOnClickListener{HandlePersonSixVisibility()}

        binding.addPerson7.setOnClickListener{HandlePersonSevenVisibility()}
        binding.addPerson8.setOnClickListener{HandlePersonEightVisibility()}
        binding.addPerson9.setOnClickListener{HandlePersonNineVisibility()}
        binding.addPerson10.setOnClickListener{HandlePersonTenVisibility()}
        binding.addMoreDetails.setOnClickListener{HandleMoreDetailsVisibility()}
        binding.btnNormalPrint.setOnClickListener { sendDataToNormalActivity() }
        binding.btnContractPrint.setOnClickListener { sendDataToContractActivity() }

    }

    private fun showTimePicker() {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePicker = TimePickerDialog(
            requireContext(),
            { _, selectedHour, selectedMinute ->
                // تنسيق الوقت بنظام 12 ساعة مع AM/PM
                val formattedTime = String.format(
                    Locale("en"),
                    "%02d:%02d %s",
                    if (selectedHour % 12 == 0) 12 else selectedHour % 12, // تحويل الساعة لصيغة 12 ساعة
                    selectedMinute,
                    if (selectedHour < 12) "AM" else "PM" // تحديد AM أو PM
                )
                binding.arriveTimeTv.text = formattedTime.toEditable() // عرض الوقت في TextView
            },
            hour,
            minute,
            false // استخدام نظام 12 ساعة
        )
        timePicker.show()
    }


    private fun HandleMoreDetailsVisibility() {
        binding.addMoreDetailsContainer.isVisible=true
        binding.addMoreDetailsContainer2.isVisible=true
    }

    private fun HandlePersonOneVisibility() {
        binding.person1.isVisible=true
        binding.addPerson2.isVisible=true
    }
    private fun HandlePersonTwoVisibility() {
            binding.person2.isVisible=true
            binding.addPerson3.isVisible=true
        }
    private fun HandlePersonThreeVisibility() {
        binding.person3.isVisible=true
        binding.addPerson4.isVisible=true
    }

    private fun HandlePersonFourVisibility() {
        binding.person4.isVisible=true
        binding.addPerson5.isVisible=true
    }
    private fun HandlePersonFiveVisibility() {
        binding.person5.isVisible=true
        binding.addPerson6.isVisible=true
    }
    private fun HandlePersonSixVisibility() {
        binding.person6.isVisible=true
        binding.addPerson7.isVisible=true
    }
    private fun HandlePersonSevenVisibility() {
        binding.person7.isVisible=true
        binding.addPerson8.isVisible=true
    }
    private fun HandlePersonEightVisibility() {
        binding.person8.isVisible=true
        binding.addPerson9.isVisible=true
    }
    private fun HandlePersonNineVisibility() {
        binding.person9.isVisible=true
        binding.addPerson10.isVisible=true
    }
    private fun HandlePersonTenVisibility() {
        binding.person10.isVisible=true
    }


    private fun showDateBicker() {
        val picker = DatePickerDialog(
            requireActivity(),
            { _, year, month, day ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, day)

                // إعداد التنسيق بالعربي
                val arabicLocale = Locale("ar")
                val dateFormat = SimpleDateFormat("yyyy/MM/dd", arabicLocale)
                val dayFormat = SimpleDateFormat("EEEE", arabicLocale) // استخراج اسم اليوم

                val formattedDate = dateFormat.format(calendar.time) // التاريخ المختار
                val formattedDay = dayFormat.format(calendar.time) // اليوم المختار

                // تعيين النصوص
                binding.dateTv.text = formattedDate.toEditable()
                binding.dayTv.text = formattedDay.toEditable()
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        picker.show()
    }

    private fun setCurrentDate() {
        val calendar = Calendar.getInstance()
        val arabicLocale = Locale("ar")
        val dateFormat = SimpleDateFormat("yyyy/MM/dd", arabicLocale)
        val dayFormat = SimpleDateFormat("EEEE", arabicLocale) // تنسيق للحصول على اسم اليوم
        val formattedDate = dateFormat.format(calendar.time)
        val formattedDay = dayFormat.format(calendar.time) // استخراج اسم اليوم

        // تعيين التاريخ واليوم إلى العناصر المطلوبة
        binding.dateTv.text = formattedDate
        binding.dayTv.text = formattedDay.toEditable()
    }


    private fun initializeCarSpinner() {
        val vehicleDao = AppDatabase.getDatabase(requireContext().applicationContext).vehicleDao()

        vehicleDao.getAllVehicleNames().observe(viewLifecycleOwner) { vehicleNames ->
            val adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item,
                vehicleNames
            )
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.carSpinner.adapter = adapter

            // Add OnItemSelectedListener
            binding.carSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long,
                ) {
                    val selectedCarName = vehicleNames[position].trim()
                    getDateofCar(selectedCarName)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    // Do nothing if no item is selected
                }
            }
        }
    }

    private fun initializeDriverSpinner() {
        val driverDao = AppDatabase.getDatabase(requireContext().applicationContext).driverDao()

        driverDao.getAllDriverNames().observe(viewLifecycleOwner) { driverNames ->
            val adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item,
                driverNames
            )
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.driverSpinner.adapter = adapter

            // Add OnItemSelectedListener
            binding.driverSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long,
                ) {
                    val selectedDriverName = driverNames[position].trim()
                    getDriverfData(selectedDriverName)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    // Do nothing if no item is selected
                }
            }
        }
    }

    private fun getDriverfData(selectedDriverName: String) {
        val driverDataDao = AppDatabase.getDatabase(requireContext().applicationContext).driverDao()

        driverDataDao.getDriversByName(selectedDriverName).observe(viewLifecycleOwner) { driver ->
            if (driver != null) {
                binding.driverPhoneTv.text = driver.phoneNumber?.toEditable() ?: "غير متوفر".toEditable()
                binding.driverIdTv.text = driver.idNumber?.toEditable() ?: "غير متوفر".toEditable()
            } else {
                binding.driverPhoneTv.text = "لم يتم العثور على السائق".toEditable()
            }
        }
    }

    private fun getDateofCar(carName: String) {
        val carDataDao = AppDatabase.getDatabase(requireContext().applicationContext).vehicleDao()

        carDataDao.getCarByName(carName).observe(viewLifecycleOwner) { vehicle ->
            if (vehicle != null) {
                binding.carPalteTv.text = vehicle.model?.toEditable() ?: "غير متوفر".toEditable()
            } else {
                binding.carPalteTv.text = "لم يتم العثور على السيارة".toEditable()
            }
        }
    }

    private fun loadSavedData() {
        // Safely load and set saved data into the fields
        val carName = sharedPreferences.getString("car_name", "")
        binding.carSpinner.setSelection(getSpinnerIndex(binding.carSpinner, carName))

        val driverPhone = sharedPreferences.getString("driver_Phone", "غير متوفر")
        binding.driverPhoneTv.text = driverPhone?.toEditable()

        val driverId = sharedPreferences.getString("driver_Id", "غير متوفر")
        binding.driverIdTv.text = driverId?.toEditable()

        val driverName = sharedPreferences.getString("driver_name", "")
        binding.driverSpinner.setSelection(getSpinnerIndex(binding.driverSpinner, driverName))

        val carModel = sharedPreferences.getString("car_model", "غير متوفر")
        binding.carPalteTv.text = carModel?.toEditable()

        // Setting other fields
        binding.clientNameTv.text = sharedPreferences.getString("client_Name", "")?.toEditable()
        binding.hagezNumTv.text = sharedPreferences.getString("hagez_Number", "")?.toEditable()
        binding.dayTv.text = sharedPreferences.getString("day", "")?.toEditable()
        binding.dateTv.text = sharedPreferences.getString("date", "")?.toEditable()
        binding.tripStartTv.text = sharedPreferences.getString("start_Trip", "")?.toEditable()
        binding.tripEndTv.text = sharedPreferences.getString("end_Trip", "")?.toEditable()
        binding.phoneNumberTv.text = sharedPreferences.getString("client_Phone", "")?.toEditable()
        binding.tripNumTv.text = sharedPreferences.getString("trip_Number", "")?.toEditable()
        binding.arriveTimeTv.text = sharedPreferences.getString("arrival_Time", "")?.toEditable()
        binding.arrivalHallTv.text = sharedPreferences.getString("arriva_lHall", "")?.toEditable()
    }

    private fun getSpinnerIndex(spinner: AdapterView<*>, value: String?): Int {
        if (value == null) return 0

        val adapter = spinner.adapter
        if (adapter is ArrayAdapter<*>) {
            for (i in 0 until adapter.count) {
                if (adapter.getItem(i).toString() == value) {
                    return i
                }
            }
        }
        return 0
    }


    private fun sendDataToNormalActivity() {
        val selectedCarName = binding.carSpinner.selectedItem?.toString()
        val driverPhone = binding.driverPhoneTv.text.toString()
        val driverId = binding.driverIdTv.text.toString()
        val selectedDriverName = binding.driverSpinner.selectedItem?.toString()
        val carModel = binding.carPalteTv.text.toString()
        val clientName = binding.clientNameTv.text.toString()
        val hagezNumber = binding.hagezNumTv.text.toString()
        val day = binding.dayTv.text.toString()
        val date = binding.dateTv.text.toString()
        val startTrip = binding.tripStartTv.text.toString()
        val endTrip = binding.tripEndTv.text.toString()
        val clientPhone = binding.phoneNumberTv.text.toString()
        val tripNumber = binding.tripNumTv.text.toString()
        val arrivalTime = binding.arriveTimeTv.text.toString()
        val arrivalHall = binding.arrivalHallTv.text.toString()

        // Save data to SharedPreferences
        val editor = sharedPreferences.edit()
        editor.putString("car_name", selectedCarName)
        editor.putString("driver_Phone", driverPhone)
        editor.putString("driver_Id", driverId)
        editor.putString("driver_name", selectedDriverName)
        editor.putString("car_model", carModel)

        editor.putString("client_Name", clientName)
        editor.putString("hagez_Number", hagezNumber)
        editor.putString("day", day)
        editor.putString("date", date)
        editor.putString("start_Trip", startTrip)
        editor.putString("end_Trip", endTrip)
        editor.putString("client_Phone", clientPhone)
        editor.putString("trip_Number", tripNumber)
        editor.putString("arrival_Time", arrivalTime)
        editor.putString("arriva_lHall", arrivalHall)
        editor.apply()

        // Navigate to the appropriate activity
        val intent = Intent(requireContext(), OperationNormalPrintActivity::class.java).apply {
            putExtra("car_name", selectedCarName)
            putExtra("driver_Phone", driverPhone)
            putExtra("driver_Id", driverId)
            putExtra("driver_name", selectedDriverName)
            putExtra("car_model", carModel)
            putExtra("client_Name", clientName)
            putExtra("hagez_Number", hagezNumber)
            putExtra("day", day)
            putExtra("date", date)
            putExtra("start_Trip", startTrip)
            putExtra("end_Trip", endTrip)
            putExtra("client_Phone", clientPhone)
            putExtra("trip_Number", tripNumber)
            putExtra("arrival_Time", arrivalTime)
            putExtra("arriva_lHall", arrivalHall)
        }
        startActivity(intent)
    }

    private fun sendDataToContractActivity() {
        val selectedCarName = binding.carSpinner.selectedItem?.toString()
        val driverPhone = binding.driverPhoneTv.text.toString()
        val driverId = binding.driverIdTv.text.toString()
        val selectedDriverName = binding.driverSpinner.selectedItem?.toString()
        val carModel = binding.carPalteTv.text.toString()
        val clientName = binding.clientNameTv.text.toString()
        val hagezNumber = binding.hagezNumTv.text.toString()
        val day = binding.dayTv.text.toString()
        val date = binding.dateTv.text.toString()
        val startTrip = binding.tripStartTv.text.toString()
        val endTrip = binding.tripEndTv.text.toString()
        val clientPhone = binding.phoneNumberTv.text.toString()
        val tripNumber = binding.tripNumTv.text.toString()
        val arrivalTime = binding.arriveTimeTv.text.toString()
        val arrivalHall = binding.arrivalHallTv.text.toString()
       val clientId_num= binding.clientIdNumTv.text.toString()

        val personOneName=binding.personName1Tv.text.toString()
        val personOneNationality=binding.personNationality1Tv.text.toString()
        val personOneid=binding.personIdNum1Tv.text.toString()

        val personTwoName=binding.personName2Tv.text.toString()
        val personTwoNationality=binding.personNationality2Tv.text.toString()
        val personTwoid=binding.personIdNum2Tv.text.toString()

        val personThreeName=binding.personName3Tv.text.toString()
        val personThreeNationality=binding.personNationality3Tv.text.toString()
        val personThreeid=binding.personIdNum3Tv.text.toString()

        val personFourName=binding.personName4Tv.text.toString()
        val personFourNationality=binding.personNationality4Tv.text.toString()
        val personFourid=binding.personIdNum4Tv.text.toString()

        val personFiveName=binding.personName5Tv.text.toString()
        val personFiveNationality=binding.personNationality5Tv.text.toString()
        val personFiveid=binding.personIdNum5Tv.text.toString()

        val personSixName=binding.personName6Tv.text.toString()
        val personSixNationality=binding.personNationality6Tv.text.toString()
        val personSixid=binding.personIdNum6Tv.text.toString()

        val personSevenName=binding.personName7Tv.text.toString()
        val personSevenNationality=binding.personNationality7Tv.text.toString()
        val personSevenid=binding.personIdNum7Tv.text.toString()

        val personEightName=binding.personName8Tv.text.toString()
        val personEightNationality=binding.personNationality8Tv.text.toString()
        val personEightid=binding.personIdNum8Tv.text.toString()

        val personNineName=binding.personName9Tv.text.toString()
        val personNineNationality=binding.personNationality9Tv.text.toString()
        val personNineid=binding.personIdNum9Tv.text.toString()

        val personTenName=binding.personName10Tv.text.toString()
        val personTenNationality=binding.personNationality10Tv.text.toString()
        val personTenid=binding.personIdNum10Tv.text.toString()


        val clientNationality = binding.clientNationalityTv.text.toString()
        val ClientInNumberNumber = binding.clientIdNumTv.text.toString()
        val tripPrice = binding.tripPriceTv.text.toString()
        val tripDuration = binding.tripDurationTv.text.toString()

        // Save data to SharedPreferences
        val editor = sharedPreferences.edit()
        editor.putString("car_name", selectedCarName)
        editor.putString("driver_Phone", driverPhone)
        editor.putString("driver_Id", driverId)
        editor.putString("driver_name", selectedDriverName)
        editor.putString("car_model", carModel)

        editor.putString("client_Name", clientName)
        editor.putString("hagez_Number", hagezNumber)
        editor.putString("day", day)
        editor.putString("date", date)
        editor.putString("start_Trip", startTrip)
        editor.putString("end_Trip", endTrip)
        editor.putString("client_Phone", clientPhone)
        editor.putString("trip_Number", tripNumber)
        editor.putString("arrival_Time", arrivalTime)
        editor.putString("arriva_lHall", arrivalHall)
        editor.apply()

        // Navigate to contract print activity
        val intent = Intent(requireContext(), OperationContractPrintActivity::class.java).apply {
            putExtra("car_name", selectedCarName)
            putExtra("driver_Phone", driverPhone)
            putExtra("driver_Id", driverId)
            putExtra("driver_name", selectedDriverName)
            putExtra("car_model", carModel)
            putExtra("client_Name", clientName)
            putExtra("hagez_Number", hagezNumber)
            putExtra("day", day)
            putExtra("date", date)
            putExtra("start_Trip", startTrip)
            putExtra("end_Trip", endTrip)
            putExtra("client_Phone", clientPhone)
            putExtra("trip_Number", tripNumber)
            putExtra("arrival_Time", arrivalTime)
            putExtra("arriva_lHall", arrivalHall)
            putExtra("clientId_num",clientId_num)

            putExtra("personOneName", personOneName)
            putExtra("personOneNationality", personOneNationality)
            putExtra("personOneid", personOneid)
            putExtra("personTwoName", personTwoName)
            putExtra("personTwoNationality", personTwoNationality)
            putExtra("personTwoid", personTwoid)
            putExtra("personThreeName", personThreeName)
            putExtra("personThreeNationality", personThreeNationality)
            putExtra("personThreeid", personThreeid)

            putExtra("personFourName", personFourName)
            putExtra("personFourNationality", personFourNationality)
            putExtra("personFourid", personFourid)

            putExtra("personFiveName", personFiveName)
            putExtra("personFiveNationality", personFiveNationality)
            putExtra("personFiveid", personFiveid)

            putExtra("personSixName", personSixName)
            putExtra("personSixNationality", personSixNationality)
            putExtra("personSixid", personSixid)

            putExtra("personSevenName", personSevenName)
            putExtra("personSevenNationality", personSevenNationality)
            putExtra("personSevenid", personSevenid)

            putExtra("personEightName", personEightName)
            putExtra("personEightNationality", personEightNationality)
            putExtra("personEightid", personEightid)

            putExtra("personNineName", personNineName)
            putExtra("personNineNationality", personNineNationality)
            putExtra("personNineid", personNineid)

            putExtra("personTenName", personTenName)
            putExtra("personTenNationality", personTenNationality)
            putExtra("personTenid", personTenid)




            putExtra("clientNationality", clientNationality)
            putExtra("ClientInNumberNumber", ClientInNumberNumber)
            putExtra("tripPrice", tripPrice)
            putExtra("tripDuration", tripDuration)


        }
        startActivity(intent)
    }

    private fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)
}
