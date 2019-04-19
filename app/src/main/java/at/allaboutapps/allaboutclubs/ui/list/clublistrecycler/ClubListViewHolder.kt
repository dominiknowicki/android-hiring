package at.allaboutapps.allaboutclubs.ui.list.clublistrecycler

import android.support.v7.widget.RecyclerView
import android.view.View
import at.allaboutapps.allaboutclubs.R
import at.allaboutapps.allaboutclubs.api.models.Club
import at.allaboutapps.allaboutclubs.utils.loadWithGlide
import kotlinx.android.synthetic.main.viewholder_list_item.view.*

class ClubListViewHolder(itemView: View, var callback: ClubListRecyclerCallback) : RecyclerView.ViewHolder(itemView) {

    private lateinit var club: Club

    fun bind(club: Club) {
        this.club = club
        itemView.setOnClickListener { callback.onClick(club) }
        setupLayout()
    }

    private fun setupLayout() {
        itemView.nameTV.text = club.name
        itemView.countryTV.text = club.country
        itemView.valueTV.text = itemView.context.getString(R.string.millions, club.value)
        itemView.imageIV.loadWithGlide(club.image)
    }
}