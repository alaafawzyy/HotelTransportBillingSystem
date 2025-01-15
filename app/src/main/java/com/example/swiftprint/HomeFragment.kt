package com.example.swiftprint

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.cardview.widget.CardView
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController


class HomeFragment : Fragment() {

    private lateinit var navController: NavController
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()

         view.findViewById<CardView>(R.id.hotel_card).setOnClickListener {
            navController.navigate(R.id.action_mainFragment_to_hotelFragment)
        }

        view.findViewById<CardView>(R.id.operation_card).setOnClickListener {
           navController.navigate(R.id.action_homeFragment_to_operationFragment)
      }

        view.findViewById<CardView>(R.id.transport_card).setOnClickListener {
            navController.navigate(R.id.action_homeFragment_to_transportationManagementFragment)
        }


        view.findViewById<CardView>(R.id.price_card).setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_pricesTransportFragment)
        }


}}