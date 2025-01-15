package com.example.swiftprint.fragment.operation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.fragment.findNavController
import com.example.swiftprint.R
import com.example.swiftprint.databinding.FragmentOperationBinding

class OperationFragment : Fragment() {
    lateinit var binding: FragmentOperationBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
       binding=FragmentOperationBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
      view.findViewById<CardView>(R.id.data_operation).setOnClickListener {
          findNavController().navigate(R.id.action_operationFragment_to_operationInsertInformationFragment)
      }
        view.findViewById<CardView>(R.id.operation_print).setOnClickListener {
            findNavController().navigate(R.id.action_operationFragment_to_operationDataFormFragment)
        }
    }

}

/*
class OperationNormalDataFragment : Fragment() {
    lateinit var binding: FragmentOperationNormalDataBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentOperationNormalDataBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // استرجاع البيانات عند فتح الشاشة
        loadDataFromPreferences()


        binding.btnPrient.setOnClickListener {
            NavigateToOperationNormalPrint()
        }
    }

    private fun NavigateToOperationNormalPrint() {
        // حفظ البيانات
        saveDataToPreferences()


        val carType = binding.carTypeTv.text.toString()
        val numberOfBoard = binding.numberOfBoardTv.text.toString()
        val driverName = binding.driverNameTv.text.toString()
        val idNumber = binding.idNamberTv.text.toString()
        val phoneNumber = binding.phoneNumberTv.text.toString()

        val intent = Intent(requireContext(), OperationNormalPrintActivity::class.java).apply {
            putExtra("car_type", carType)
            putExtra("number_of_board", numberOfBoard)
            putExtra("driver_name", driverName)
            putExtra("id_number", idNumber)
            putExtra("phone_number", phoneNumber)
        }
        startActivity(intent)
    }


    private fun saveDataToPreferences() {
        val sharedPreferences = requireContext().getSharedPreferences("OperationData", Context.MODE_PRIVATE)
        sharedPreferences.edit().apply {
            putString("car_type", binding.carTypeTv.text.toString())
            putString("number_of_board", binding.numberOfBoardTv.text.toString())
            putString("driver_name", binding.driverNameTv.text.toString())
            putString("id_number", binding.idNamberTv.text.toString())
            putString("phone_number", binding.phoneNumberTv.text.toString())
            apply() // تأكيد الحفظ
        }
    }


    private fun loadDataFromPreferences() {
        val sharedPreferences = requireContext().getSharedPreferences("OperationData", Context.MODE_PRIVATE)
        binding.carTypeTv.setText(sharedPreferences.getString("car_type", ""))
        binding.numberOfBoardTv.setText(sharedPreferences.getString("number_of_board", ""))
        binding.driverNameTv.setText(sharedPreferences.getString("driver_name", ""))
        binding.idNamberTv.setText(sharedPreferences.getString("id_number", ""))
        binding.phoneNumberTv.setText(sharedPreferences.getString("phone_number", ""))
    }
}

 */