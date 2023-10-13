package com.opensystem.smallwork.ui.fragments.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getDrawable
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.opensystem.smallwork.R
import com.opensystem.smallwork.databinding.FragmentMainBinding
import com.opensystem.smallwork.ui.fragments.main.chat.ChatFrag
import com.opensystem.smallwork.ui.fragments.main.contracts.ContractsFrag
import com.opensystem.smallwork.ui.fragments.main.home.HomeFrag
import com.opensystem.smallwork.ui.fragments.main.menu.MenuFrag

/**
 * A simple [Fragment] subclass.
 */
class MainFrag : Fragment() {

   private lateinit var fragmentMainBinding: FragmentMainBinding

   override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?
   ): View {
      // Inflate the layout for this fragment
      fragmentMainBinding = FragmentMainBinding.inflate(inflater, container, false)
      startComponents()
      return fragmentMainBinding.root
   }

   private fun startComponents() = with(fragmentMainBinding) {
      val pagerAdapter: FragmentStateAdapter = ScreenSlidePagerAdapter(requireActivity())
      pager.adapter = pagerAdapter

      TabLayoutMediator(navigationTab, pager) { tab: TabLayout.Tab, position: Int ->
         when (position) {
            0 -> tab.icon = getDrawable(requireContext(), R.drawable.tab_item_home)
            1 -> tab.icon = getDrawable(requireContext(), R.drawable.tab_item_contract)
            2 -> tab.icon = getDrawable(requireContext(), R.drawable.tab_item_chat)
            3 -> tab.icon = getDrawable(requireContext(), R.drawable.tab_item_menu)
         }
      }.attach()
   }

   class ScreenSlidePagerAdapter(fa: FragmentActivity?) : FragmentStateAdapter(fa!!) {
      override fun createFragment(position: Int): Fragment {
         return when (position) {
            0 -> HomeFrag()
            1 -> ContractsFrag()
            2 -> ChatFrag()
            3 -> MenuFrag()
            else -> HomeFrag()
         }
      }

      override fun getItemCount(): Int {
         return 4
      }
   }
}