package com.opensystem.smallwork.ui.dialogs

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import com.opensystem.smallwork.R
import com.opensystem.smallwork.addTextMoneyMaskListener

/**
 * AppMessage
 *
 * A class of Wesley Brito
 *
 * @author Android version Wesley Brito
 */

@SuppressLint("UseCompatLoadingForDrawables")
object AppMessage {
   fun error(context: Context, msg: String) {
      val alertDialog = AlertDialog.Builder(context).create()
      val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
      val view: View = inflater.inflate(R.layout.dialog_alert, null)

      alertDialog.setView(view)
      alertDialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
      alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
      alertDialog.show()

      val confirmTxt = view.findViewById<TextView>(R.id.confirmTxt)
      val titleTxt = view.findViewById<TextView>(R.id.titleTxt)
      val msgTxt = view.findViewById<TextView>(R.id.msgTxt)

      titleTxt.text = context.getString(R.string.failure)
      msgTxt.text = msg

      confirmTxt.setOnClickListener { alertDialog.dismiss() }
   }

   fun error(context: Context, msg: String, onOkClickListener: () -> Unit) {
      val alertDialog = AlertDialog.Builder(context).create()
      val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
      val view: View = inflater.inflate(R.layout.dialog_alert, null)

      alertDialog.setView(view)
      alertDialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
      alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
      alertDialog.show()

      val confirmTxt = view.findViewById<TextView>(R.id.confirmTxt)
      val titleTxt = view.findViewById<TextView>(R.id.titleTxt)
      val msgTxt = view.findViewById<TextView>(R.id.msgTxt)

      titleTxt.text = context.getString(R.string.failure)
      msgTxt.text = msg

      confirmTxt.setOnClickListener {
         onOkClickListener()
         alertDialog.dismiss()
      }
   }

   fun activePro(context: Context, onOkClickListener: () -> Unit) {
      val alertDialog = AlertDialog.Builder(context).create()
      val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
      val view: View = inflater.inflate(R.layout.dialog_alert, null)

      alertDialog.setView(view)
      alertDialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
      alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
      alertDialog.show()

      val alertImg = view.findViewById<ImageView>(R.id.alertImg)
      val confirmTxt = view.findViewById<TextView>(R.id.confirmTxt)
      val titleTxt = view.findViewById<TextView>(R.id.titleTxt)
      val msgTxt = view.findViewById<TextView>(R.id.msgTxt)
      val llCancel = view.findViewById<LinearLayout>(R.id.ll_cancel)

      titleTxt.text = context.getString(R.string.advertise_your_work)
      msgTxt.text = context.getString(R.string.active_pro_msg)
      alertImg.setImageDrawable(context.getDrawable(R.drawable.ic_ad_alert))

      llCancel.apply {
         isVisible = true
         setOnClickListener { alertDialog.dismiss() }
      }

      confirmTxt.apply {
         text = context.getString(R.string.confirm)
         setOnClickListener {
            onOkClickListener()
            alertDialog.dismiss()
         }
      }
   }

   fun confirmRequest(context: Context, onOkClickListener: () -> Unit) {
      val alertDialog = AlertDialog.Builder(context).create()
      val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
      val view: View = inflater.inflate(R.layout.dialog_alert, null)

      alertDialog.setView(view)
      alertDialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
      alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
      alertDialog.show()

      val alertImg = view.findViewById<ImageView>(R.id.alertImg)
      val confirmTxt = view.findViewById<TextView>(R.id.confirmTxt)
      val titleTxt = view.findViewById<TextView>(R.id.titleTxt)
      val msgTxt = view.findViewById<TextView>(R.id.msgTxt)
      val llCancel = view.findViewById<LinearLayout>(R.id.ll_cancel)

      titleTxt.text = context.getString(R.string.confirm_request)
      msgTxt.text = context.getString(R.string.confirm_request_msg)
      alertImg.isVisible = false

      llCancel.apply {
         isVisible = true
         setOnClickListener { alertDialog.dismiss() }
      }

      confirmTxt.apply {
         text = context.getString(R.string.confirm)
         setOnClickListener {
            onOkClickListener()
            alertDialog.dismiss()
         }
      }
   }

   fun replyRequest(
      context: Context,
      accept: Boolean,
      onOkClickListener: () -> Unit
   ) {
      val alertDialog = AlertDialog.Builder(context).create()
      val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
      val view: View = inflater.inflate(R.layout.dialog_alert, null)

      alertDialog.setView(view)
      alertDialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
      alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
      alertDialog.show()

      val alertImg = view.findViewById<ImageView>(R.id.alertImg)
      val confirmTxt = view.findViewById<TextView>(R.id.confirmTxt)
      val titleTxt = view.findViewById<TextView>(R.id.titleTxt)
      val msgTxt = view.findViewById<TextView>(R.id.msgTxt)
      val llCancel = view.findViewById<LinearLayout>(R.id.ll_cancel)

      titleTxt.text = if (accept) context.getString(R.string.accept_request) else context.getText(R.string.decline_request)
      msgTxt.text = if (accept) context.getString(R.string.accept_request_msg) else context.getText(R.string.decline_request_msg)

      alertImg.isVisible = false

      llCancel.apply {
         isVisible = true
         setOnClickListener { alertDialog.dismiss() }
      }

      confirmTxt.apply {
         text = if (accept) context.getString(R.string.confirm) else context.getString(R.string.reject)
         setOnClickListener {
            onOkClickListener()
            alertDialog.dismiss()
         }
      }
   }

   fun success(context: Context, msg: String, click: MessageClick) {
      val alertDialog = AlertDialog.Builder(context).create()
      val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
      val view: View = inflater.inflate(R.layout.dialog_alert, null)

      alertDialog.setView(view)
      alertDialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
      alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
      alertDialog.setCanceledOnTouchOutside(false)
      alertDialog.setCancelable(false)
      alertDialog.show()

      val alertImg = view.findViewById<ImageView>(R.id.alertImg)
      val confirmTxt = view.findViewById<TextView>(R.id.confirmTxt)
      val titleTxt = view.findViewById<TextView>(R.id.titleTxt)
      val msgTxt = view.findViewById<TextView>(R.id.msgTxt)

      alertImg.setImageDrawable(context.getDrawable(R.drawable.ic_success_full))
      titleTxt.text = context.getString(R.string.success)
      msgTxt.text = msg

      confirmTxt.setOnClickListener {
         click.onClickListener()
         alertDialog.dismiss()
      }

   }

   fun termsAndConditions(context: Context, termsAndConditionsClick: TermsAndConditionsClick) {
      val alertDialog = AlertDialog.Builder(context).create()
      val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
      val view: View = inflater.inflate(R.layout.dialog_terms, null)

      alertDialog.setView(view)
      alertDialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
      alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
      alertDialog.show()

      val btnConfirm = view.findViewById<TextView>(R.id.btn_confirm)
      val btnBack = view.findViewById<ImageButton>(R.id.btn_back)

      btnConfirm.setOnClickListener {
         termsAndConditionsClick.onClickListener(true)
         alertDialog.dismiss()
      }

      btnBack.setOnClickListener { alertDialog.dismiss() }

   }

   fun setDailyValue(context: Context, onOkClickListener: (String) -> Unit) {
      val alertDialog = AlertDialog.Builder(context).create()
      val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
      val view: View = inflater.inflate(R.layout.dialog_edit_value_daily, null)

      alertDialog.setView(view)
      alertDialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
      alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
      alertDialog.show()

      val confirmTxt = view.findViewById<TextView>(R.id.txt_confirm)
      val editValue = view.findViewById<EditText>(R.id.edit_value)
      editValue.addTextMoneyMaskListener()

      confirmTxt.apply {
         text = context.getString(R.string.confirm)
         setOnClickListener {
            onOkClickListener(editValue.text.toString())
            alertDialog.dismiss()
         }
      }
   }

   @Deprecated("use Unit")
   interface MessageClick {
      fun onClickListener()
   }

   @Deprecated("use Unit")
   interface TermsAndConditionsClick {
      fun onClickListener(isConfirmed: Boolean)
   }
}