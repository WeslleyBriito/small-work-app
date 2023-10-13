package com.opensystem.smallwork.ui.fragments.main.contracts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.opensystem.smallwork.databinding.FragmentContractsBinding
import com.opensystem.smallwork.ui.adapters.ContractAdapter
import com.opensystem.smallwork.ui.dialogs.AppMessage
import com.opensystem.smallwork.utils.Preferences

class ContractsFrag : Fragment() {
   private var _binding: FragmentContractsBinding? = null
   // This property is only valid between onCreateView and
   // onDestroyView.
   private val binding get() = _binding!!

   private val viewModel: ContractsViewModels by viewModels()

   override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?
   ): View {
      // Inflate the layout for this fragment
      _binding = FragmentContractsBinding.inflate(inflater, container, false)
      return binding.root
   }

   override fun onResume() {
      super.onResume()
      startComponents()
   }

   private fun startComponents() = with(binding) {
      if (Preferences(requireContext()).proActivated()) return@with

      flContainerMsg.isVisible = false

      setViewModelObserver()

      viewModel.getContractsById(requireContext())
   }

   private fun setViewModelObserver() = with(viewModel){

      contracts.observe(viewLifecycleOwner){ list ->
         binding.llProgressBar.isVisible = false
         binding.recycler.apply {
            adapter = ContractAdapter(
               list,
               {},
               { finish, contract -> AppMessage.replyRequest(context, finish){
                  binding.llProgressBar.isVisible = true
                  viewModel.replyRequest(context, finish, contract)
               }}
            )
         }
      }

      noContracts.observe(viewLifecycleOwner){ response ->
         with(binding){
            llProgressBar.isVisible = false
            flContainerMsg.isVisible = true
            appContainerMsg.txtTitle.isVisible = false
            appContainerMsg.imgMsg.isVisible = false
            appContainerMsg.txtMsg.text = response
         }
      }

      acceptRequestResponse.observe(viewLifecycleOwner) {
         Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
         viewModel.getContractsById(requireContext())
      }

      declineRequestResponse.observe(viewLifecycleOwner) {
         Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
         viewModel.getContractsById(requireContext())
      }
   }
}