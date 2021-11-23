package com.alpas.coordinator.ui.custom.behaviors

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.alpas.coordinator.ui.custom.CustomFab
import com.google.android.material.floatingactionbutton.FloatingActionButton

/**
 * Created by Oleksiy Pasmarnov on 23.11.21
 */
class HideFabBehavior() : CoordinatorLayout.Behavior<FloatingActionButton>() {
    constructor(ctx: Context, attrs: AttributeSet) : this()

    override fun onDependentViewChanged(
        parent: CoordinatorLayout,
        child: FloatingActionButton,
        dependency: View
    ): Boolean {
        dependency as CustomFab
        return if (dependency.isChecked && dependency.isOrWillBeHidden) {
            child.hide()
            true
        } else if (dependency.isChecked) {
            child.show()
            true
        } else {
            false
        }
    }
}