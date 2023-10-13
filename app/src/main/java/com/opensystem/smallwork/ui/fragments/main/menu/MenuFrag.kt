package com.opensystem.smallwork.ui.fragments.main.menu

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.opensystem.smallwork.R
import com.opensystem.smallwork.SplashActivity
import com.opensystem.smallwork.databinding.FragmentMenuBinding
import com.opensystem.smallwork.ui.activities.AuthAct
import com.opensystem.smallwork.ui.dialogs.AppMessage
import com.opensystem.smallwork.ui.fragments.main.MainFragDirections
import com.opensystem.smallwork.utils.Preferences
import com.opensystem.smallwork.utils.appUser
import com.squareup.picasso.Picasso

/**
 * create an instance of this fragment.
 */
class MenuFrag : Fragment() {

   private val viewModel: MenuViewModel by viewModels()

   private var _binding: FragmentMenuBinding? = null

   // This property is only valid between onCreateView and
   // onDestroyView.
   private val binding get() = _binding!!

   override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?
   ): View {
      // Inflate the layout for this fragment
      _binding = FragmentMenuBinding.inflate(inflater, container, false)
      startComponents()
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      observer()
      setClicks()
   }

   override fun onDestroyView() {
      super.onDestroyView()
      _binding = null
   }

   private fun startComponents() = with(binding){
      if (appUser.avatar != null) Picasso.get().load(appUser.avatar).into(imgAvatar)
      txtName.text = appUser.name
      txtEmail.text = appUser.email
      rating.rating = if (appUser.rating != null) appUser.rating!! else 5f

      txtActiveUserPro.setText(viewModel.getTextToAd(Preferences(context)))
   }

   private fun observer() {
      viewModel.hasSignOut.observe(viewLifecycleOwner) {
         if (it) {
            startActivity(Intent(activity, AuthAct::class.java))
            activity?.finish()
         }
      }

      viewModel.userUpdatedSuccess.observe(viewLifecycleOwner){
         if (it){
            Toast.makeText(requireContext(), getString(R.string.you_are_professional_now), Toast.LENGTH_SHORT).show()
            startActivity(Intent(activity, SplashActivity::class.java))
            activity?.finish()
         }else {
            Toast.makeText(requireContext(), "service is down, please try again later", Toast.LENGTH_SHORT).show()
            startActivity(Intent(activity, SplashActivity::class.java))
            activity?.finish()
         }
      }
   }

   private fun setClicks() = with(binding) {
      profileCard.setOnClickListener {
         findNavController().navigate(MainFragDirections.actionNavMainToProfileFrag())
      }

      txtActiveUserPro.setOnClickListener {
         if (!appUser.professional){
            AppMessage.activePro(requireContext()){
               setDailyValue()
            }
         }
         else {
            Preferences(requireContext()).setProActivated(!Preferences(context).proActivated())
            startActivity(Intent(activity, SplashActivity::class.java))
            activity?.finish()
         }
      }

      txtLogout.setOnClickListener {
         viewModel.signOut(requireContext())
      }
   }

   private fun setDailyValue(){
      AppMessage.setDailyValue(requireContext()){
         viewModel.updatedUser(requireContext(), it)
      }
   }

}