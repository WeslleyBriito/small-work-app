package com.opensystem.smallwork.ui.fragments.auth.login

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.opensystem.smallwork.R
import com.opensystem.smallwork.databinding.FragmentLoginBinding
import com.opensystem.smallwork.ui.activities.MainAct
import com.opensystem.smallwork.ui.dialogs.AppMessage
import com.opensystem.smallwork.utils.Validation

/**
 * create an instance of this fragment.
 */
class LoginFrag : Fragment() {

   private lateinit var auth: FirebaseAuth

   private val _viewModel: LoginViewModel by viewModels()

   private var _binding: FragmentLoginBinding? = null
   private val binding get() = _binding!!

   override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?
   ): View {
      // Inflate the layout for this fragment
      _binding = FragmentLoginBinding.inflate(inflater, container, false)
      startComponents()
      return binding.root
   }

   private fun startComponents() = with(binding) {
      _viewModel.initRepositories(requireContext())
      binding.loginBtn.setOnClickListener {
         if (!validate()) return@setOnClickListener
         authUser()
      }

      binding.btnBack.setOnClickListener { requireActivity().onBackPressed() }

      observer()
   }

   private fun authUser() {
      auth = Firebase.auth
      auth.signInWithEmailAndPassword(
         binding.editEmail.text.toString(),
         binding.editPassword.text.toString()
      ).addOnCompleteListener(requireActivity()) { task ->
         // Sign in success, get user data
         if (task.isSuccessful) {
            val id = auth.currentUser?.uid
            if (id != null) _viewModel.getUserData(requireContext(), id)
         } else {

            val exception = try {
               throw task.exception!!
            } catch (e: FirebaseAuthInvalidUserException) {
               getString(R.string.email_or_password_incorrect)
            } catch (e: Exception) {
               getString(R.string.error) + ": " + e.message
            }

            AppMessage.error(requireContext(), exception)
         }
      }
   }

   private fun observer() {
      _viewModel.saveUser.observe(viewLifecycleOwner) {
         if (it) {
            startHome()
         } else {
            auth.signOut()
            AppMessage.error(requireContext(), getString(R.string.error_get_user_data))
         }
      }
   }

   private fun validate(): Boolean {
      if (!Validation.email(requireContext(), binding.editEmail)) return false
      else if (!Validation.texField(requireContext(), binding.editPassword)) {
         binding.editPassword.error = getString(R.string.type_your_password)
         return false
      }
      return true
   }

   private fun startHome() {
      val intent = Intent(requireContext(), MainAct::class.java)
      intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or
            Intent.FLAG_ACTIVITY_CLEAR_TOP or
            Intent.FLAG_ACTIVITY_CLEAR_TASK or
            Intent.FLAG_ACTIVITY_SINGLE_TOP
      intent.putExtra("login", true)
      startActivity(intent)
      requireActivity().finish()
   }
}