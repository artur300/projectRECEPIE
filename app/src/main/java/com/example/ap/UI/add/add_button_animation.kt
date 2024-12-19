package com.example.ap.UI.add

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator

object add_button_animation {
    fun scaleView(view: View, duration: Long = 300, onEnd: (() -> Unit)? = null) {
        // Create scale animations
        val scaleX = ObjectAnimator.ofFloat(view, "scaleX", 1f, 1.2f, 1f)
        val scaleY = ObjectAnimator.ofFloat(view, "scaleY", 1f, 1.2f, 1f)

        // Set the duration and interpolator
        scaleX.duration = duration
        scaleY.duration = duration
        scaleX.interpolator = AccelerateDecelerateInterpolator()
        scaleY.interpolator = AccelerateDecelerateInterpolator()

        // Play animations together
        val animatorSet = AnimatorSet()
        animatorSet.playTogether(scaleX, scaleY)

        // Listener לסיום האנימציה
        animatorSet.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {}
            override fun onAnimationEnd(animation: Animator) {
                onEnd?.invoke() // קורא ל-Callback אם הוא לא null
            }
            override fun onAnimationCancel(animation: Animator) {}
            override fun onAnimationRepeat(animation: Animator) {}
        })

        animatorSet.start()
    }
}

