package com.opensystem.smallwork.utils

import android.content.Context
import android.util.Patterns
import android.widget.EditText
import com.opensystem.smallwork.R
import java.util.*

/**
 * Useful
 *
 * @author Android version Wesley Brito
 * @Created 2023/08/05
 */
class Validation {
   companion object {
      private fun isEmpty(editText: EditText?): Boolean {
         if (editText == null) {
            return false
         }
         if (editText.hint == null) {
            return !editText.text.toString().isEmpty() || editText.text
               .toString().isNotEmpty()
         }
         if (editText.text.toString().isEmpty() && editText.text.toString().isEmpty()) {
            error(editText, editText.hint.toString() + " deve ser preenchido(a).")
            return false
         }
         return true
      }

      fun password(context: Context, editText: EditText): Boolean {
         if (editText.text.toString().isEmpty()) {
            error(editText, context.getString(R.string.type_your_password))
            return false
         }
         if (editText.text.length < 6) {
            error(editText, context.getString(R.string.small_password))
            return false
         }
         if (!Useful.strongPassword(editText.text.toString(), 1)) {
            error(editText, context.getString(R.string.no_number_password))
            return false
         }
         return true
      }

      private fun isValidEmail(target: CharSequence?): Boolean {
         return if (target == null) false else Patterns.EMAIL_ADDRESS.matcher(target).matches()
      }

      fun data(editText: EditText): Boolean {
         if (editText.text.toString().isEmpty()) {
            error(editText, "Data deve ser preenchida.")
            return false
         }
         if (editText.text.toString().length < 10) {
            error(editText, "Data deve ser preenchida de forma completa.")
            return false
         }
         if (!Useful.dataValida(editText.text.toString())) {
            error(editText, "Data deve estar em um formato válido.")
            return false
         }
         return true
      }

      /**
       * Se o nome for valido ele retorna verdadeiro
       */
      fun name(context: Context, editText: EditText): Boolean {
         if (editText.text.toString().isEmpty()) {
            error(editText, context.getString(R.string.type_your_name))
            return false
         }
         if (Useful.strongPassword(editText.text.toString(), 1)) {
            error(editText, context.getString(R.string.contain_number_name))
            return false
         }
         return true
      }

      fun email(context: Context, editText: EditText): Boolean {
         if (editText.text.toString().isEmpty()) {
            error(editText, context.getString(R.string.type_your_email))
            return false
         }
         if (!isValidEmail(editText.text.toString())) {
            error(editText, context.getString(R.string.email_invalid))
            return false
         }
         return true
      }

      fun coPassword(context: Context, pass1: EditText, pass2: EditText): Boolean {
         if (pass1.text.toString() != pass2.text.toString()) {
            error(pass2, context.getString(R.string.password_not_match))
            return false
         }
         return true
      }

      private fun error(editText: EditText?, texto: String) {
         if (editText == null) {
            return
         }
         editText.error = texto
         AppAnimations.shake(editText)
         editText.requestFocus()
      }

      fun texField(context: Context, editText: EditText): Boolean {
         if (!isEmpty(editText) || editText.error != null) {
            error(editText, context.getString(R.string.type_your, editText.hint))
            return false
         }
         return true
      }
   }
}