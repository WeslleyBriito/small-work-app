package com.opensystem.smallwork.ui.fragments.auth.signup

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.opensystem.smallwork.R
import com.opensystem.smallwork.databinding.FragmentSignUpBinding
import com.opensystem.smallwork.models.User
import com.opensystem.smallwork.ui.activities.MainAct
import com.opensystem.smallwork.ui.dialogs.AppMessage
import com.opensystem.smallwork.utils.AppAnimations
import com.opensystem.smallwork.utils.Validation

/**
 * create an instance of this fragment.
 */
class SignUpFrag : Fragment() {

   private val TAG = "firebaseResult"

   private lateinit var mViewModel: SignUpViewModel

   private var _binding: FragmentSignUpBinding? = null
   val binding get() = _binding!!

   private lateinit var auth: FirebaseAuth

   override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?
   ): View {
      // Inflate the layout for this fragment
      mViewModel = ViewModelProvider(this)[SignUpViewModel::class.java]
      _binding = FragmentSignUpBinding.inflate(inflater, container, false)

      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)

      binding.checkBox.setOnClickListener {
         binding.checkBox.isChecked = false
         AppMessage.termsAndConditions(
            requireContext(),
            object : AppMessage.TermsAndConditionsClick {
               override fun onClickListener(isConfirmed: Boolean) {
                  binding.checkBox.isChecked = isConfirmed
               }
            })
      }

      binding.btnSingUp.setOnClickListener {
         if (!validate()) {
            return@setOnClickListener
         }
         mViewModel.register(
            binding.editName.text.toString(),
            binding.editEmail.text.toString(),
            binding.editPassword.text.toString()
         )
      }

      observe()

      binding.btnBack.setOnClickListener { requireActivity().onBackPressed() }
   }

   private fun observe() {
      mViewModel.user.observe(viewLifecycleOwner) {
         addNewUser(it)
      }
   }

   private fun addNewUser(user: User) {
      auth = Firebase.auth

      auth.createUserWithEmailAndPassword(user.email, user.password)
         .addOnCompleteListener(requireActivity()) { task ->
            if (task.isSuccessful) {
               Log.d(TAG, "createUserWithEmail: " + auth.currentUser?.uid)

               user.id = auth.currentUser?.uid.toString()
               mViewModel.save(requireContext(), user)

               AppMessage.success(requireContext(), getString(R.string.success_sign_up),
                  object : AppMessage.MessageClick {
                     override fun onClickListener() {
                        startHome()
                     }
                  })
            } else {

               val exception = try {
                  throw task.exception!!
               } catch (e: FirebaseAuthUserCollisionException) {
                  getString(R.string.user_collision_exception)
               } catch (e: Exception) {
                  getString(R.string.user_sign_up_exception) + ": " + e.message
               }

               AppMessage.error(requireContext(), exception)
               Log.w(TAG, "error: $exception")
            }
         }
   }

   private fun startHome() {
      val intent = Intent(context, MainAct::class.java)
      intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or
            Intent.FLAG_ACTIVITY_CLEAR_TOP or
            Intent.FLAG_ACTIVITY_CLEAR_TASK or
            Intent.FLAG_ACTIVITY_SINGLE_TOP
      intent.putExtra("login", true)
      startActivity(intent)
   }

   private fun validate(): Boolean {
      if (!Validation.name(requireContext(), binding.editName)) return false
      if (!Validation.email(requireContext(), binding.editEmail)) return false
      if (!Validation.password(requireContext(), binding.editPassword)) return false
      if (!Validation.password(requireContext(), binding.editCoPassword)) return false
      if (!Validation.coPassword(
            requireContext(),
            binding.editPassword,
            binding.editCoPassword
         )
      ) return false
      return if (!binding.checkBox.isChecked) {
         AppAnimations.shake(binding.checkBox)
         Toast.makeText(context, getString(R.string.accept_use_terms), Toast.LENGTH_LONG).show()
         false
      } else true
   }
}