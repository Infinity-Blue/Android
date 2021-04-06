import android.R
import android.app.Activity
import android.graphics.Rect
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.ScrollView

/*
@brief  Layout is adjusted when Keyboard appears
 */
class AndroidBug5497Workaround constructor(var activity: Activity, svChildLayoutId: Int) {
    private val mChildOfContent: View
    private var usableHeightPrevious = 0
    private val frameLayoutParams: FrameLayout.LayoutParams
    var svChildLayout: View
    var originalGravity: Int
    private fun possiblyResizeChildOfContent() {
        val usableHeightNow = computeUsableHeight()
        if (usableHeightNow != usableHeightPrevious) {
            val usableHeightSansKeyboard = mChildOfContent.rootView.height
            val heightDifference = usableHeightSansKeyboard - usableHeightNow
            if (heightDifference > usableHeightSansKeyboard / 4) {
                Log.d("keyboard status ","visible")
                // keyboard probably just became visible
                onKeyboardVisible()
                frameLayoutParams.height = usableHeightSansKeyboard - heightDifference
            } else {
                Log.d("keyboard status ","Invisible")
                // keyboard probably just became hidden
                onKeyboardHidden()
                frameLayoutParams.height = usableHeightSansKeyboard
            }
            mChildOfContent.requestLayout()
            usableHeightPrevious = usableHeightNow
        }
    }


    private fun computeUsableHeight(): Int {
        val r = Rect()
        mChildOfContent.getWindowVisibleDisplayFrame(r)
        return r.bottom - r.top
    }


    private fun onKeyboardVisible() {
        val params =
            svChildLayout.layoutParams as FrameLayout.LayoutParams
        params.gravity = Gravity.TOP
        svChildLayout.requestLayout()
        val parentSv = svChildLayout.parent as ScrollView
        parentSv.post {
            val focusedEditText = activity.window.currentFocus
            parentSv.smoothScrollTo(0, focusedEditText!!.top)
        }
    }


    private fun onKeyboardHidden() {
        val params =
            svChildLayout.layoutParams as FrameLayout.LayoutParams
        params.gravity = originalGravity
        svChildLayout.requestLayout()
    }


    companion object {
        // For more information, see https://code.google.com/p/android/issues/detail?id=5497
        // To use this class, simply invoke assistActivity() on an Activity that already has its content view set.
        fun assistActivity(activity: Activity, svChildLayoutId: Int) {
            AndroidBug5497Workaround(activity, svChildLayoutId)
        }
    }


    init {
        svChildLayout = activity.findViewById(svChildLayoutId)
        originalGravity = (svChildLayout.layoutParams as FrameLayout.LayoutParams).gravity
        val content =
            activity.findViewById<View>(R.id.content) as FrameLayout
        mChildOfContent = content.getChildAt(0)
        mChildOfContent.viewTreeObserver
            .addOnGlobalLayoutListener { possiblyResizeChildOfContent() }
        frameLayoutParams = mChildOfContent.layoutParams as FrameLayout.LayoutParams
    }
}