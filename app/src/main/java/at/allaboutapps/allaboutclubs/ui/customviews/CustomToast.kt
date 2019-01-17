package at.allaboutapps.allaboutclubs.ui.customviews

import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import at.allaboutapps.allaboutclubs.R
import at.allaboutapps.allaboutclubs.utils.getDpAsInt

class CustomToast @JvmOverloads constructor(context: Context, marginBottom: Int = 75) {

    private val toast: Toast?
    private val textView: TextView

    init {
        val layout = LinearLayout(context)
        textView = TextView(context)

        setLayoutStyle(layout)
        setTextViewStyle()

        layout.addView(textView)

        toast = Toast(context)
        toast.view = layout
        toast.setGravity(Gravity.BOTTOM, 0, getDpAsInt(context, marginBottom))
    }

    private fun setLayoutStyle(layout: LinearLayout) {
        layout.setBackgroundResource(R.drawable.shape_background_toast)
    }

    private fun setTextViewStyle() {
        textView.setTextColor(Color.WHITE)
        textView.textSize = 14f
        val padding = getDpAsInt(textView.context, 8)
        textView.setPadding(padding, padding, padding, padding)
        textView.gravity = Gravity.CENTER
    }

    fun show(msg: String) {
        textView.text = msg
        toast?.show()
    }
}
