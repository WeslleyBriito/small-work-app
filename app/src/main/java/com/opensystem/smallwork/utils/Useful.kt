package com.opensystem.smallwork.utils

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.provider.MediaStore
import java.util.*

object Useful {
   private const val ALLOWED_CHARACTERS = "0123456789qwertyuiopasdfghjklzxcvbnm"

   fun strongPassword(password: String, type: Int): Boolean {
      var result = false
      var findNumber = false
      /*boolean achouMaiuscula = false;
      boolean achouMinuscula = false;
      boolean achouSimbolo = false;*/for (c in password.toCharArray()) {
         if (c in '0'..'9') {
            findNumber = true
         } /*else if (c >= 'A' && c <= 'Z') {
                achouMaiuscula = true;
            } else if (c >= 'a' && c <= 'z') {
                achouMinuscula = true;
            } else {
                achouSimbolo = true;
            }*/
         if (type == 1) result = findNumber

         /*if (tipo == 2) resultado = achouMaiuscula;

         if (tipo == 3) resultado = achouMinuscula;

         if (tipo == 4) resultado = achouSimbolo;*/
      }
      return result
   }

   fun dataValida(string: String): Boolean {
      var check1 = false
      var check2 = false
      var check3 = false
      val separado = string.split("/").toTypedArray()
      if (separado.size != 3) {
         return false
      }
      val dia = separado[0].toInt()
      val mes = separado[1].toInt()
      val ano = separado[2].toInt()
      if (dia in 1..31) check1 = true
      if (mes in 1..12) check2 = true
      if (ano >= 1900 && mes <= 2050) check3 = true
      return check1 && check2 && check3
   }

   fun picturePath(context: Context, data: Intent?): String? {
      val selectedImage = data?.data
      val filePathColumn =
         arrayOf(MediaStore.Images.Media.DATA)
      val cursor =
         context.contentResolver.query(selectedImage!!, filePathColumn, null, null, null)
      cursor?.moveToFirst()
      val columnIndex = cursor?.getColumnIndex(filePathColumn[0])
      val picturePath = cursor?.getString(columnIndex!!)
      cursor?.close()
      return picturePath
   }

   fun bmOptions(): BitmapFactory.Options {

      return BitmapFactory.Options().apply {
         inJustDecodeBounds = true
         inJustDecodeBounds = false
         inSampleSize = 3
      }

   }

   fun getRandomString(sizeOfRandomString: Int): String {
      val random = Random()
      val sb = java.lang.StringBuilder(sizeOfRandomString)
      for (i in 0 until sizeOfRandomString) sb.append(
         ALLOWED_CHARACTERS[random.nextInt(
            ALLOWED_CHARACTERS.length
         )]
      )
      return sb.toString()
   }

   fun formatCEP(cep: String): String {
      val digits = cep.replace("[^\\d]".toRegex(), "")
      val formattedCEP = buildString {
         if (digits.length > 5) {
            append(digits.substring(0, 5))
            append("-")
            append(digits.substring(5))
         } else {
            append(digits)
         }
      }
      return formattedCEP
   }
}