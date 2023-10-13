package com.opensystem.smallwork.ui.dialogs

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import androidx.appcompat.app.AlertDialog
import com.opensystem.smallwork.R
import com.opensystem.smallwork.databinding.DialogEditProfileDataBinding
import com.opensystem.smallwork.models.User
import com.opensystem.smallwork.utils.Validation
import com.opensystem.smallwork.utils.appUser

object EditProfileDataDialog{

    fun editUserData(context: Context, itemClick: (User)-> Unit ){
        val alertDialog = AlertDialog.Builder(context).create()
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View = inflater.inflate(R.layout.dialog_edit_profile_data, null)
        val binding = DialogEditProfileDataBinding.bind(view)
        alertDialog.setView(view)
        alertDialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        alertDialog.show()

        binding.editName.setText(appUser.name)
        binding.editDescription.setText(appUser.description)
        binding.editProfession.setText(appUser.profession)

        binding.txtConfirm.setOnClickListener {

            if (!Validation.name(context, binding.editName)) return@setOnClickListener
            if (!Validation.texField(context, binding.editProfession)) return@setOnClickListener
            if (!Validation.texField(context, binding.editDescription)) return@setOnClickListener

            appUser.profession = binding.editProfession.text.toString()
            appUser.description = binding.editDescription.text.toString()
            appUser.name = binding.editName.text.toString()

            itemClick(appUser)
            alertDialog.dismiss()
        }

        binding.txtBack.setOnClickListener { alertDialog.dismiss() }
    }

}