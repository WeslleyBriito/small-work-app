package com.opensystem.smallwork.ui.dialogs

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import androidx.appcompat.app.AlertDialog
import com.opensystem.smallwork.R
import com.opensystem.smallwork.apis.ApiResult
import com.opensystem.smallwork.apis.viacep.ControllerVC
import com.opensystem.smallwork.database.address.UAddressRepository
import com.opensystem.smallwork.databinding.DialogEditAddressBinding
import com.opensystem.smallwork.models.ErrorSW
import com.opensystem.smallwork.models.UAddress
import com.opensystem.smallwork.models.ViaCep
import com.opensystem.smallwork.utils.Useful
import com.opensystem.smallwork.utils.Validation
import com.opensystem.smallwork.utils.appUser

object EditAddressDialog {

   fun editAddress(context: Context, itemClick:(UAddress) -> Unit) {
      val alertDialog = AlertDialog.Builder(context).create()
      val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
      val view: View = inflater.inflate(R.layout.dialog_edit_address, null)
      val binding = DialogEditAddressBinding.bind(view)
      alertDialog.setView(view)
      alertDialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
      alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
      alertDialog.show()

      val address = if (appUser.addressId != null) {
         UAddressRepository(context).get(appUser.addressId!!)
      } else {
         UAddress()
      }

      with(binding){
         editPostalCode.setText(address.postalCode)
         editCity.setText(address.city)
         editState.setText(address.state)
         editNbh.setText(address.nbh)
         editStreet.setText(address.street)
         editNumber.setText(address.number)
         editComp.setText(address.complement)

         editPostalCode.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
               if (s.toString().length != 9) {
                  editPostalCode.removeTextChangedListener(this)
                  val formattedText = Useful.formatCEP(s.toString())
                  editPostalCode.setText(formattedText)
                  editPostalCode.setSelection(formattedText.length)
                  editPostalCode.addTextChangedListener(this)
               }
            }

            override fun afterTextChanged(s: Editable?) {
               if (s.toString().length == 9) {
                  ControllerVC.getAddressByCep(
                     editPostalCode.text.toString(),
                     object : ApiResult<ViaCep> {
                        override fun responseBody(body: ViaCep, type: String) {
                           editCity.setText(body.city)
                           editState.setText(body.state)
                           editNbh.setText(body.nbh)
                           editStreet.setText(body.street)
                        }

                        override fun error(error: ErrorSW) {}
                     }
                  )
               }
            }
         })

         txtConfirm.setOnClickListener {

            if (!Validation.texField(context, editPostalCode)) return@setOnClickListener
            if (!Validation.texField(context, editCity)) return@setOnClickListener
            if (!Validation.texField(context, editState)) return@setOnClickListener
            if (!Validation.texField(context, editNbh)) return@setOnClickListener
            if (!Validation.texField(context, editStreet)) return@setOnClickListener
            if (!Validation.texField(context, editNumber)) return@setOnClickListener

            address.postalCode = editPostalCode.text.toString()
            address.city = editCity.text.toString()
            address.state = editState.text.toString()
            address.nbh = editNbh.text.toString()
            address.street = editStreet.text.toString()
            address.number = editNumber.text.toString()
            address.complement = editComp.text.toString()

            itemClick(address)
            alertDialog.dismiss()
         }

         binding.txtBack.setOnClickListener { alertDialog.dismiss() }
      }

   }

}