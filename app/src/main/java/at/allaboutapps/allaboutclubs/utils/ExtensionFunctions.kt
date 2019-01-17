package at.allaboutapps.allaboutclubs.utils

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.RecyclerView
import android.widget.ImageView
import at.allaboutapps.allaboutclubs.R
import at.allaboutapps.allaboutclubs.api.models.Club
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import java.util.*

internal fun getDpAsInt(context: Context, dpValue: Int): Int {
    val d = context.resources.displayMetrics.density
    return (dpValue * d).toInt()
}

fun ImageView.loadWithGlide(url: String?) {
    Glide.with(this)
            .applyDefaultRequestOptions(RequestOptions().placeholder(R.drawable.club_placeholder).error(R.drawable.club_placeholder))
            .load(url)
            .into(this)
}

fun RecyclerView.setHorizontalDivider(drawable: Int) {
    val verticalDecoration = DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL)
    ContextCompat.getDrawable(this.context, drawable)?.let {
        verticalDecoration.setDrawable(it)
        this.addItemDecoration(verticalDecoration)
    }
}

enum class SortOrder { ASC, DESC }

fun SortOrder.switch(): SortOrder {
    return if (this == SortOrder.ASC)
        SortOrder.DESC
    else
        SortOrder.ASC
}

fun LinkedList<Club>.sort(order: SortOrder?) {
    if (order == SortOrder.DESC)
        this.sortByDescending { club -> club.name }
    else
        this.sortBy { club -> club.name }
}
