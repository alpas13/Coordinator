package com.alpas.coordinator.ui.custom.behaviors

import android.util.Log
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.marginEnd
import androidx.core.view.marginRight
import com.alpas.coordinator.ui.custom.ArticleSubmenu
import com.alpas.coordinator.ui.custom.Bottombar

/**
 * Created by Oleksiy Pasmarnov on 22.11.21
 */
class SubmenuBehavior: CoordinatorLayout.Behavior<ArticleSubmenu>() {
    override fun layoutDependsOn(
        parent: CoordinatorLayout,
        child: ArticleSubmenu,
        dependency: View
    ): Boolean {
        return dependency is Bottombar
    }

    override fun onDependentViewChanged(
        parent: CoordinatorLayout,
        child: ArticleSubmenu,
        dependency: View
    ): Boolean {
        return if (child.isOpen && dependency is Bottombar && dependency.translationY >= 0) {
            animate(child, dependency)
            true
        } else false
    }

    private fun animate(child: ArticleSubmenu, dependency: Bottombar) {
            val fraction = dependency.translationY / dependency.minHeight
            child.translationX = (child.width + child.marginEnd) * fraction
            Log.d("M_SubmenuBehavior","fraction: $fraction / transX: ${child.translationX}")
    }

}