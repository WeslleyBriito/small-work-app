package com.opensystem.smallwork.ui.fragments.auth.signin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.opensystem.smallwork.R
import com.opensystem.smallwork.databinding.FragmentSignInBinding

/**
 * create an instance of this fragment.
 */
class SignInFrag : Fragment() {

   private var _binding: FragmentSignInBinding? = null
   private val binding get() = _binding!!

   override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?
   ): View {
      // Inflate the layout for this fragment
      _binding = FragmentSignInBinding.inflate(inflater, container, false)

      binding.loginEmailBtn.setOnClickListener {
         it.findNavController().navigate(R.id.action_sign_in_to_login)
      }
      binding.singUpTxt.setOnClickListener {
         it.findNavController().navigate(R.id.action_sign_in_to_sign_up)
      }

      return binding.root
   }

   override fun onDestroyView() {
      super.onDestroyView()
      _binding = null
   }

}