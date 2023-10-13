package com.opensystem.smallwork.utils

import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.opensystem.smallwork.R

/**
 * AppAnimations
 *
 * A class of Wesley Brito
 *
 * @author Android version Wesley Brito
 * @Created  2023/08/05
 */

object AppAnimations {
   fun bounce(view: View) {
      val rotation: Animation = AnimationUtils.loadAnimation(view.context, R.anim.bounce)
      rotation.repeatCount = 3
      view.startAnimation(rotation)
   }

   fun shake(view: View) {
      val rotation: Animation = AnimationUtils.loadAnimation(view.context, R.anim.bounce)
      rotation.repeatCount = Animation.INFINITE
      view.startAnimation(rotation)
   }

   fun rotate(view: View) {
      val rotation: Animation = AnimationUtils.loadAnimation(view.context, R.anim.rotate)
      rotation.repeatCount = Animation.INFINITE
      view.startAnimation(rotation)
   }
}