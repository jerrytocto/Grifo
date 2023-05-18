package com.juliandev.grifo.ui.view.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View

import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.juliandev.grifo.R
import com.juliandev.grifo.databinding.FragmentPruebaBinding
import com.juliandev.grifo.ui.viewModel.PruebaViewModel


class PruebaFragment : Fragment(R.layout.fragment_prueba) {

    private val pruebaViewModel:PruebaViewModel by viewModels()
    private var _binding: FragmentPruebaBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentPruebaBinding.bind(view)

        pruebaViewModel.text.observe(viewLifecycleOwner, Observer {
            binding.textPrueba.text=it
        })
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}