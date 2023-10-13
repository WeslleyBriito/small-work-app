package com.opensystem.smallwork

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import java.text.NumberFormat

/**
 * EDIT_TEXT
 */

fun EditText.addTextMoneyMaskListener() {
   val editText = this
   var current = ""

   editText.addTextChangedListener(object : TextWatcher {
      override fun afterTextChanged(arg0: Editable) {}
      override fun beforeTextChanged(
         s: CharSequence, start: Int,
         count: Int, after: Int
      ) {
      }

      override fun onTextChanged(
         s: CharSequence, start: Int,
         before: Int, count: Int
      ) {
         if (s.toString() != current) {
            editText.removeTextChangedListener(this)

            val cleanString: String = s.toString().replace("""[^0-9]""".toRegex(), "")

            if (cleanString.isNotEmpty()) {
               val parsed = cleanString.toDouble() / 100
               val formatted = NumberFormat.getCurrencyInstance().format(parsed)

               current = formatted
               editText.setText(formatted)
               editText.setSelection(formatted.length)
            } else {
               current = ""
               editText.setText("")
            }

            editText.addTextChangedListener(this)
         }
      }
   })
}

// VIEWS

fun View.getColorCompat(@ColorRes color: Int): Int {
   return ContextCompat.getColor(this.context, color)
}
