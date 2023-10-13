package com.opensystem.smallwork.ui.fragments.main.menu.profile.details

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.opensystem.smallwork.R
import com.opensystem.smallwork.databinding.FragmentDetailsBinding
import com.opensystem.smallwork.ui.dialogs.AppMessage
import com.opensystem.smallwork.ui.dialogs.EditAddressDialog
import com.opensystem.smallwork.ui.dialogs.EditProfileDataDialog
import com.opensystem.smallwork.utils.Permission
import com.opensystem.smallwork.utils.Useful
import com.opensystem.smallwork.utils.appUAddressName
import com.opensystem.smallwork.utils.appUser
import com.squareup.picasso.Picasso

/**
 * create an instance of this fragment.
 */
class DetailsFrag : Fragment() {

   private lateinit var _binding: FragmentDetailsBinding
   private val binding get() = _binding

   private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
      if (Permission.hasPermissions(permissions, requireContext())) {
         val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
         getContent.launch(intent)
      } else {
         handlePermissionsDenied()
      }
   }

   private val _viewModel: DetailsViewModel by viewModels()

   private val getContent =
      registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
         if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data

            val image = BitmapFactory.decodeFile(
               Useful.picturePath(requireContext(), data),
               Useful.bmOptions()
            )

            if (image != null) {
               binding.imgAvatar.setImageBitmap(image)
               _viewModel.updateAvatar(image)
            }

         }
      }

   override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?
   ): View {
      // Inflate the layout for this fragment
      _binding = FragmentDetailsBinding.inflate(inflater, container, false)
      startComponents()
      _viewModel.updateUserSuccess.observe(viewLifecycleOwner){
         startComponents()
      }
      return binding.root
   }

   private fun startComponents() = with(binding) {
      if (appUser.avatar != null) {
         Picasso.get().load(appUser.avatar).into(binding.imgAvatar)
      }

      txtName.text = appUser.name
      txtProfession.text = appUser.profession
      txtDescription.text = appUser.description
      txtAddress.text = appUAddressName
      editValue.text = appUser.dailyValue
      llValueContainer.isVisible = appUser.professional

      enableClicks()
   }

   private fun enableClicks() = with(binding) {
      imgAvatar.setOnClickListener {
         if (Permission.hasNoPermissions(permissions, requireContext())){
            requestPermissionLauncher.launch(permissions)
            return@setOnClickListener
         }
         val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
         getContent.launch(intent)
      }
      txtProfession.setOnClickListener {
         editUser()
      }
      txtName.setOnClickListener {
         editUser()
      }
      txtDescription.setOnClickListener {
         editUser()
      }
      txtAddress.setOnClickListener {
         EditAddressDialog.editAddress(requireContext()) {
            _viewModel.editAddress(it)
         }
      }
      llValueContainer.setOnClickListener {
         setDailyValue()
      }
   }

   private fun editUser() {
      EditProfileDataDialog.editUserData(
         requireContext(),
      ) {
         _viewModel.updatedUser(it, requireContext())
      }
   }

   private fun setDailyValue(){
      AppMessage.setDailyValue(requireContext()){
         _viewModel.updatedUserValue(requireContext(), it)
      }
   }

   private fun handlePermissionsDenied() {
      AppMessage.error(
         context = requireContext(),
         msg = getString(R.string.you_need_permissions),
      )
   }

   companion object {
      private val permissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) arrayOf(
         Manifest.permission.READ_MEDIA_IMAGES,
         Manifest.permission.CAMERA
      ) else arrayOf(
         Manifest.permission.READ_EXTERNAL_STORAGE,
         Manifest.permission.WRITE_EXTERNAL_STORAGE,
         Manifest.permission.CAMERA
      )
   }
}