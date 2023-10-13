package com.opensystem.smallwork.ui.fragments.main.home.worker

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.opensystem.smallwork.R
import com.opensystem.smallwork.databinding.FragmentWorkerBinding
import com.opensystem.smallwork.models.Worker
import com.opensystem.smallwork.ui.dialogs.AppMessage
import com.opensystem.smallwork.ui.fragments.main.home.worker.contacts.ProContactsFrag
import com.opensystem.smallwork.ui.fragments.main.home.worker.datails.ProDetailsFrag
import com.opensystem.smallwork.ui.fragments.main.home.worker.rating.ProRatingFrag
import com.squareup.picasso.Picasso

/**
 * A simple [Fragment] subclass.
 */
class WorkerFrag : Fragment() {

   private var _binding: FragmentWorkerBinding? = null

   // This property is only valid between onCreateView and
   // onDestroyView.
   private val binding get() = _binding!!

   private val viewModel: WorkerViewModel by viewModels()
   private val args: WorkerFragArgs by navArgs()

   override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?
   ): View {
      // Inflate the layout for this fragment
      _binding = FragmentWorkerBinding.inflate(inflater, container, false)
      startComponents()
      return binding.root
   }

   private fun startComponents() = with(binding) {
      /**
       * Instantiate a ViewPager and a PagerAdapter.
       * The pager adapter, which provides the pages to the view pager widget.
       */
      val pagerAdapter: FragmentStateAdapter =
         ScreenSlidePagerAdapter(requireActivity(), args.worker)
      appContainerPro.pager.adapter = pagerAdapter

      TabLayoutMediator(tabLayout, appContainerPro.pager) { tab: TabLayout.Tab, position: Int ->
         when (position) {
            0 -> tab.text = getString(R.string.details)
            1 -> tab.text = getString(R.string.ratings)
            2 -> tab.text = getString(R.string.contacts)
         }
      }.attach()

      if (args.worker.avatar != null) Picasso.get().load(args.worker.avatar).into(imgAvatar)
      txtName.text = args.worker.name
      txtProfession.text = args.worker.profession
      toolbar.setOnClickListener { requireActivity().onBackPressed() }
      txtRequest.setOnClickListener {
         AppMessage.confirmRequest(requireContext()) {
            txtRequest.isEnabled = false
            Toast.makeText(
               requireContext(),
               getString(R.string.ordering_please_wait),
               Toast.LENGTH_SHORT
            ).show()
            viewModel.request(args.worker, requireContext())
         }
      }

      setViewModelObservers()
   }

   private fun setViewModelObservers() = with(viewModel) {
      success.observe(viewLifecycleOwner) {
         if (it){
            AppMessage.success(requireContext(), getString(R.string.requests_success), object: AppMessage.MessageClick{
               override fun onClickListener() {
                  requireActivity().onBackPressed()
               }
            })
         } else{
            AppMessage.error(requireContext(), "failed to make request"){
               requireActivity().onBackPressed()
            }
         }
      }
   }

   class ScreenSlidePagerAdapter(fa: FragmentActivity, var worker: Worker) :
      FragmentStateAdapter(fa) {
      override fun createFragment(position: Int): Fragment {
         return when (position) {
            0 -> ProDetailsFrag.startWith(worker)
            1 -> ProRatingFrag()
            else -> ProContactsFrag()
         }
      }

      override fun getItemCount(): Int {
         return 3
      }
   }
}