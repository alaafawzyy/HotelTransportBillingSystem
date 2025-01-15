package com.example.swiftprint.fragment.price

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.navigation.fragment.findNavController
import com.example.swiftprint.R
import com.example.swiftprint.databinding.FragmentPricesTransportBinding


class PricesTransportFragment : Fragment() {
   lateinit var binding:FragmentPricesTransportBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding=FragmentPricesTransportBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<CardView>(R.id.price_normal).setOnClickListener {
            goToPriceManagementFragment("normal")
        }

        view.findViewById<CardView>(R.id.price_ramadan).setOnClickListener {
            goToPriceManagementFragment("ramadan")
        }
        view.findViewById<CardView>(R.id.price_Heg).setOnClickListener {
            goToPriceManagementFragment("Hug")
        }
    }

    private fun goToPriceManagementFragment(type: String) {
        val bundle = Bundle().apply {
            putString("type", "$type")
        }
        findNavController().navigate(
            R.id.action_pricesTransportFragment_to_priceManagrmentFragment,
            bundle
        )
    }


}