package com.opensystem.smallwork.ui.fragments.main.home.request

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.opensystem.smallwork.R
import com.opensystem.smallwork.databinding.FragmentRequestBinding

class RequestFrag : Fragment() {
   private lateinit var _binding: FragmentRequestBinding
   private val binding get() = _binding

   private val viewModel: RequestViewModel by viewModels()

   override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?
   ): View {
      _binding = FragmentRequestBinding.inflate(inflater, container, false)
      return binding.root
   }

   private fun startComponents(){}
}