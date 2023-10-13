package com.opensystem.smallwork.ui.fragments.main.home.worker.datails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.opensystem.smallwork.R
import com.opensystem.smallwork.databinding.FragmentProDetailsBinding
import com.opensystem.smallwork.models.Worker

/**
 * create an instance of this fragment.
 */
class ProDetailsFrag : Fragment() {
   private var _binding: FragmentProDetailsBinding? = null
   private val binding get() = _binding!!

   override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?
   ): View {
      // Inflate the layout for this fragment
      _binding = FragmentProDetailsBinding.inflate(inflater, container, false)
      startComponents()
      return binding.root
   }

   private fun startComponents() = with(binding) {
      txtDesc.text = worker.description
      txtLocality.text = if (worker.addressName == null) getString(R.string.uninformed) else worker.addressName
      txtValue.text = "${getText(R.string.daily)} ${worker.dailyValue}"
   }

   companion object {
      private lateinit var worker: Worker

      fun startWith(wkr: Worker): ProDetailsFrag {
         worker = wkr

         return (ProDetailsFrag())
      }
   }

}