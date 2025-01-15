package com.example.swiftprint.fragment.operation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.swiftprint.databinding.FragmentOperationInsertInformationBinding
import androidx.lifecycle.lifecycleScope
import com.example.swiftprint.database.model.AppDatabase
import com.example.swiftprint.database.model.operation.Driver
import com.example.swiftprint.database.model.operation.Vehicle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OperationInsertInformationFragment : Fragment() {

    private lateinit var binding: FragmentOperationInsertInformationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentOperationInsertInformationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnCarSave.setOnClickListener {
            insertCarToDataBase()
        }
        binding.btnDriverSave.setOnClickListener {
            insertDriverToDataBase()
        }

        binding.btnCardelete.setOnClickListener {
            DeleteCarToDataBase()
        }
        binding.btnDriverdelete.setOnClickListener {
            DeleteDriverToDataBase()
        }
    }

    private fun DeleteCarToDataBase() {
        val vehicleType = binding.carTypeTv.text.toString().trim()

        val carDataDao = AppDatabase.getDatabase(requireContext().applicationContext).vehicleDao()

        // تشغيل العملية في الـ background
        CoroutineScope(Dispatchers.IO).launch {
            carDataDao.deleteCarByName(vehicleType)
        }
        binding.carTypeTv.text?.clear()
        Toast.makeText(requireContext(), "تم حذف المركبة بنجاح", Toast.LENGTH_SHORT).show()
    }

    private fun DeleteDriverToDataBase() {
        val driverName = binding.driverNameTv.text.toString().trim()

        val driverDataDao = AppDatabase.getDatabase(requireContext().applicationContext).driverDao()

        CoroutineScope(Dispatchers.IO).launch {
            driverDataDao.deleteDriverByName(driverName)
        }
        binding.driverNameTv.text?.clear()
        Toast.makeText(requireContext(), "تم حذف السائق بنجاح", Toast.LENGTH_SHORT).show()
    }


    private fun insertDriverToDataBase() {

        val drivername = binding.driverNameTv.text.toString().trim()
        val idnumber = binding.idNumberTv.text.toString().trim()
        val phonenumber = binding.phoneNumberTv.text.toString().trim()

        val driver= Driver(id=null,name = drivername.takeIf { it.isNotEmpty() },idNumber = idnumber.takeIf { it.isNotEmpty() },phoneNumber = phonenumber.takeIf { it.isNotEmpty() })

        viewLifecycleOwner.lifecycleScope.launch {
            try {
                AppDatabase.getDatabase(requireContext().applicationContext)
                    .driverDao()
                    .insertDriver(driver)
                Toast.makeText(requireContext(), "تم حفظ السائق بنجاح", Toast.LENGTH_SHORT).show()
                binding.idNumberTv.text?.clear()
                binding.driverNameTv.text?.clear()
                binding.phoneNumberTv.text?.clear()
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "حدث خطأ أثناء الحفظ: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun insertCarToDataBase() {

        val vehicleType = binding.carTypeTv.text.toString().trim()
        val plateNumber = binding.boardNumTv.text.toString().trim()
        val car = Vehicle(id = null, name = vehicleType.takeIf { it.isNotEmpty() }, model = plateNumber.takeIf { it.isNotEmpty() })

        viewLifecycleOwner.lifecycleScope.launch {
            try {
                AppDatabase.getDatabase(requireContext().applicationContext)
                    .vehicleDao()
                    .insertVehicle(car)
                Toast.makeText(requireContext(), "تم حفظ المركبة بنجاح", Toast.LENGTH_SHORT).show()

                     binding.carTypeTv.text?.clear()
                    binding.boardNumTv.text?.clear()
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "حدث خطأ أثناء الحفظ: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
    }

