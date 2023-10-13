package com.opensystem.smallwork.ui.fragments.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.opensystem.smallwork.R
import com.opensystem.smallwork.databinding.FragmentHomeBinding
import com.opensystem.smallwork.ui.adapters.CategoryAdapter
import com.opensystem.smallwork.ui.adapters.ContractAdapter
import com.opensystem.smallwork.ui.adapters.WorkerAdapter
import com.opensystem.smallwork.ui.dialogs.AppMessage
import com.opensystem.smallwork.ui.fragments.main.MainFragDirections
import com.opensystem.smallwork.utils.Preferences

class HomeFrag : Fragment() {

   private var _binding: FragmentHomeBinding? = null
   // This property is only valid between onCreateView and
   // onDestroyView.
   private val binding get() = _binding!!

   private val viewModel: HomeViewModel by viewModels()

   private val pref: Preferences by lazy { Preferences(requireContext()) }

   override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?
   ): View {
      // Inflate the layout for this fragment
      _binding = FragmentHomeBinding.inflate(inflater, container, false)
      startComponents()
      return binding.root
   }

   private fun startComponents() = with(binding){

      if (pref.proActivated()){
         txtTitle.text = getString(R.string.requests)
         viewModel.getContractsById(requireContext())
      }

      if(!pref.proActivated()){
         viewModel.getWorkers(requireContext())
         llCategContent.isVisible = true
         binding.rvCategory.apply {
            adapter = CategoryAdapter{}
         }
      }

      setViewModelObserver()
   }

   private fun setViewModelObserver() = with(viewModel){
      workers.observe(viewLifecycleOwner){ wks ->
         binding.llProgressBar.isVisible = false
         binding.recycler.apply {
            adapter = WorkerAdapter(wks){
               findNavController().navigate(MainFragDirections.actionNavMainToWorkerFrag(it))
            }
         }
      }

      noWorkers.observe(viewLifecycleOwner){ response ->
         with(binding){
            llProgressBar.isVisible = false
            llContainerMsg.isVisible = true
            appContainerMsg.txtTitle.isVisible = false
            appContainerMsg.imgMsg.isVisible = false
            appContainerMsg.txtMsg.text = response
         }
      }

      // CONTRACTS

      contracts.observe(viewLifecycleOwner){ list ->
         binding.llProgressBar.isVisible = false
         binding.recycler.apply {
            adapter = ContractAdapter(
               list,
               {},
               { accept, contract -> AppMessage.replyRequest(context, accept){
                  binding.llProgressBar.isVisible = true
                  viewModel.replyRequest(context, accept, contract)
               }}
            )
         }
      }

      noContracts.observe(viewLifecycleOwner){ response ->
         with(binding){
            llProgressBar.isVisible = false
            llContainerMsg.isVisible = true
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

   override fun onDestroyView() {
      super.onDestroyView()
      _binding = null
   }
}