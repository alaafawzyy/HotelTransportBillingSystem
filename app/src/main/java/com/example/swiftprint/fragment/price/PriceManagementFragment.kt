package com.example.swiftprint.fragment.price

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.navigation.fragment.findNavController
import com.example.swiftprint.R
import com.example.swiftprint.databinding.FragmentPriceManagementBinding
import com.example.swiftprint.fragment.operation.OperationNormalPrintActivity


class PriceManagementFragment : Fragment() {

lateinit var binding: FragmentPriceManagementBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
       binding= FragmentPriceManagementBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val priceType = arguments?.getString("type")
        val company=binding.companyNameTv.text
        
         binding.priceInsert.setOnClickListener {
        if(!binding.companyNameTv.text.isNullOrBlank()){
            binding.companyName.error=null
      goToInsertDataFragment(company,"$priceType")
      }
      else{
          binding.companyName.error="يرجي ادخال اسم الشركة"
      }
    }
        binding.pricePrint.setOnClickListener {
            if(!binding.companyNameTv.text.isNullOrBlank()){
                binding.companyName.error=null
                goToPrintDataFragment(company,"$priceType")
            }
            else{
                binding.companyName.error="يرجي ادخال اسم الشركة"
            }
        }

        binding.priceUbdate.setOnClickListener {
            if(!binding.companyNameTv.text.isNullOrBlank()){
                binding.companyName.error=null
                goToUbdateDataFragment(company,"$priceType")
            }
            else{
                binding.companyName.error="يرجي ادخال اسم الشركة"
            }
        }
        
    
    }

    private fun goToUbdateDataFragment(company: Editable?, dataType: String) {
        val intent = Intent(requireContext(), PricesUpdateActivityActivity::class.java).apply {
            putExtra("company", company?.toString())  // هنا بنحول Editable إلى String
            putExtra("dataType", dataType)
        }
        startActivity(intent)
    }

    private fun goToPrintDataFragment(company: Editable?, dataType: String) {
        val intent = Intent(requireContext(), PricesPrintActivity::class.java).apply {
            putExtra("company", company?.toString())  // هنا بنحول Editable إلى String
            putExtra("dataType", dataType)
        }
        startActivity(intent)

    }


    private fun goToInsertDataFragment(company: Editable?, dataType: String) {
        val intent = Intent(requireContext(), PricesInsertActivity::class.java).apply {
            putExtra("company", company?.toString())  // هنا بنحول Editable إلى String
            putExtra("dataType", dataType)
        }

        startActivity(intent)

    }

    private fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)
}

