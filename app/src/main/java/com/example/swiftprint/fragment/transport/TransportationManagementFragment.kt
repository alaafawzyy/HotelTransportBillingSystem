package com.example.swiftprint.fragment.transport

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.navigation.fragment.findNavController
import com.example.swiftprint.R
import com.example.swiftprint.databinding.FragmentTransportationManagementBinding


class TransportationManagementFragment : Fragment() {

lateinit var binding: FragmentTransportationManagementBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
       binding=FragmentTransportationManagementBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<CardView>(R.id.data_transport).setOnClickListener {
            findNavController().navigate(R.id.action_transportationManagementFragment_to_transportInsertDataFragment)
        }

        view.findViewById<CardView>(R.id.transport_print).setOnClickListener {
            findNavController().navigate(R.id.action_transportationManagementFragment_to_transportDataFormFragment)
        }
    }
}