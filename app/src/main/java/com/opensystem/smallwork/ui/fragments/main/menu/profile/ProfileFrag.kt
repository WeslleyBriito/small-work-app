package com.opensystem.smallwork.ui.fragments.main.menu.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.opensystem.smallwork.ui.fragments.main.menu.profile.details.DetailsFrag
import com.opensystem.smallwork.ui.fragments.main.menu.profile.sub.ContactsFrag
import com.opensystem.smallwork.ui.fragments.main.menu.profile.sub.RatingFrag
import com.opensystem.smallwork.R
import com.opensystem.smallwork.databinding.FragmentProfileBinding

/**
 * create an instance of this fragment.
 */
class ProfileFrag : Fragment() {

   private lateinit var _binding: FragmentProfileBinding
   private val binding get() = _binding

   override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?
   ): View {
      // Inflate the layout for this fragment
      _binding = FragmentProfileBinding.inflate(inflater, container, false)
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)

      /**
       * Instantiate a ViewPager and a PagerAdapter.
       * The pager adapter, which provides the pages to the view pager widget.
       */
      val pagerAdapter: FragmentStateAdapter = ScreenSlidePagerAdapter(activity)
      binding.pager.adapter = pagerAdapter

      TabLayoutMediator(binding.tabLayout, binding.pager) { tab: TabLayout.Tab, position: Int ->
         when (position) {
            0 -> tab.text = getString(R.string.details)
            1 -> tab.text = getString(R.string.ratings)
            2 -> tab.text = getString(R.string.contacts)
         }
      }.attach()

   }

   class ScreenSlidePagerAdapter(fa: FragmentActivity?) : FragmentStateAdapter(fa!!) {
      override fun createFragment(position: Int): Fragment {
         return when (position) {
            0 -> DetailsFrag()
            1 -> RatingFrag()
            else -> ContactsFrag()
         }
      }

      override fun getItemCount(): Int {
         return 3
      }
   }

}