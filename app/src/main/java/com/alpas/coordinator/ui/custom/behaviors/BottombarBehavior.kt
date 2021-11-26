package com.alpas.coordinator.ui.custom.behaviors

import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.math.MathUtils
import androidx.core.view.ViewCompat
import androidx.customview.widget.ViewDragHelper
import com.alpas.coordinator.R
import com.alpas.coordinator.ui.custom.Bottombar

/**
 * Created by Oleksiy Pasmarnov on 22.11.21
 */
class BottombarBehavior : CoordinatorLayout.Behavior<Bottombar>() {

    private var topBound = 0
    private var bottomBound = 0
    private var interceptingEvents = false
    lateinit var dragHelper: ViewDragHelper


    override fun onLayoutChild(
        parent: CoordinatorLayout,
        child: Bottombar,
        layoutDirection: Int
    ): Boolean {
        parent.onLayoutChild(child, layoutDirection)
        if (!::dragHelper.isInitialized) initialize(parent, child)
        if (child.isClose) ViewCompat.offsetTopAndBottom(child, bottomBound - topBound)
        return true
    }

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
        if (child.isClose && offset != child.translationY) child.translationY = offset
        Log.d("M_BottombarBehavior", "offset: $offset / translationY: ${child.translationY}")
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type)
    }

    override fun onInterceptTouchEvent(
        parent: CoordinatorLayout,
        child: Bottombar,
        ev: MotionEvent
    ): Boolean {
        when(ev.actionMasked) {
            MotionEvent.ACTION_DOWN -> interceptingEvents = parent.isPointInChildBounds(child, ev.x.toInt(), ev.y.toInt())
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> interceptingEvents = false
        }
        return if (interceptingEvents) dragHelper.shouldInterceptTouchEvent(ev)
        else false
    }

    override fun onTouchEvent(
        parent: CoordinatorLayout,
        child: Bottombar,
        ev: MotionEvent
    ): Boolean {
        dragHelper.processTouchEvent(ev)
        return true
    }

    private fun initialize(parent: CoordinatorLayout, child: Bottombar) {
        dragHelper = ViewDragHelper.create(parent, 1f, DragHelperCallback())
        topBound = parent.height - child.height
        bottomBound = parent.height - child.minHeight
        val webView = child.findViewById<WebView>(R.id.webview)
        webView.webViewClient = WebViewClient()
        webView.loadUrl("https://static-maps.yandex.ru/1.x/?ll=-18.783719,64.881884&size=400,300&l=sat&z=9")
    }

    inner class DragHelperCallback : ViewDragHelper.Callback() {
        override fun tryCaptureView(child: View, pointerId: Int): Boolean {
            return child is Bottombar
        }

        override fun getViewVerticalDragRange(child: View): Int {
            return bottomBound - topBound
        }

        override fun clampViewPositionVertical(child: View, top: Int, dy: Int): Int {
            return MathUtils.clamp(top, topBound, bottomBound)
        }

        override fun onViewReleased(view: View, xvel: Float, yvel: Float) {
            view as Bottombar

            // If drag down -> close
            val needClose = yvel > 0
            val startSettling = dragHelper.settleCapturedViewAt(0, if (needClose) bottomBound else topBound)
            if (startSettling) {
                ViewCompat.postOnAnimation(view, SettleRunnable(view) { view.isClose = needClose })
            } else {
                view.isClose = needClose
            }
        }

        private inner class SettleRunnable(private val view: View, private val animationEnd: () -> Unit): Runnable {
            override fun run() {
                if (dragHelper.continueSettling(true)) {
                    ViewCompat.postOnAnimation(view, this)
                } else {
                    animationEnd.invoke()
                }
            }

        }

    }
}
