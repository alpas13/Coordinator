package com.alpas.coordinator.ui.custom

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.content.res.ColorStateList
import android.os.Build
import android.util.AttributeSet
import android.widget.Checkable
import androidx.annotation.RequiresApi
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import com.alpas.coordinator.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

/**
 * Created by Oleksiy Pasmarnov on 23.11.21
 */
class CustomFab @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    FloatingActionButton(context, attrs, defStyleAttr), Checkable {
    private var checked: Boolean = false
    private val animation: AnimatorSet

    init {
        val ca = context.getColor(R.color.color_accent)
        val cw = context.getColor(android.R.color.white)

        val rotateAnim = ObjectAnimator.ofFloat(this, "rotation", 135f)
        val iconAnim = ValueAnimator.ofArgb(cw, ca)
        iconAnim.addUpdateListener { imageTintList = ColorStateList.valueOf(it.animatedValue as Int) }
        val bgAnim = ValueAnimator.ofArgb(ca, cw)
        bgAnim.addUpdateListener { backgroundTintList = ColorStateList.valueOf(it.animatedValue as Int) }

        animation = AnimatorSet().apply {
            interpolator = FastOutSlowInInterpolator()
            playTogether(rotateAnim, iconAnim, bgAnim)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun performClick(): Boolean {
        toggle()
        return super.performClick()
    }

    override fun isChecked(): Boolean = checked

    @RequiresApi(Build.VERSION_CODES.O)
    override fun toggle() {
        isChecked = !checked
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun setChecked(check: Boolean) {
        if (checked == check) return
        checked = check
        playAnimation()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun playAnimation() {
        if (isChecked) animation.start() else animation.reverse()
    }
}
