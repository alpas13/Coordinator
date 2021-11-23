package com.alpas.coordinator.ui.custom.behaviors

import android.util.Log
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.math.MathUtils
import androidx.core.view.ViewCompat
import com.alpas.coordinator.ui.custom.Bottombar

/**
 * Created by Oleksiy Pasmarnov on 22.11.21
 */
class BottombarBehavior: CoordinatorLayout.Behavior<Bottombar>() {
    override fun onStartNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: Bottombar,
        directTargetChild: View,
        target: View,
        axes: Int,
        type: Int
    ): Boolean {
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL
    }

    override fun onNestedPreScroll(
        coordinatorLayout: CoordinatorLayout,
        child: Bottombar,
        target: View,
        dx: Int,
        dy: Int,
        consumed: IntArray,
        type: Int
    ) {
        var offset = MathUtils.clamp(child.translationY + dy, 0f, child.minHeight.toFloat())
        if (offset != child.translationY) child.translationY = offset
        Log.d("M_BottombarBehavior","offset: $offset / translationY: ${child.translationY}")
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type)
    }
}