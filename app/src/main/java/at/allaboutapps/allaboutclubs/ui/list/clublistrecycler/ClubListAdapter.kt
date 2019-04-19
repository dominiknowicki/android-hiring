package at.allaboutapps.allaboutclubs.ui.list.clublistrecycler

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import at.allaboutapps.allaboutclubs.R
import at.allaboutapps.allaboutclubs.api.models.Club
import java.util.*

class ClubListAdapter(private var clubList: LinkedList<Club>, private var callback: ClubListRecyclerCallback) : RecyclerView.Adapter<ClubListViewHolder>() {

    init {
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClubListViewHolder {
        return ClubListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.viewholder_list_item, parent, false), callback)
    }

    override fun onBindViewHolder(holder: ClubListViewHolder, position: Int) {
        holder.bind(clubList[position])
    }

    fun setList(clubList: LinkedList<Club>) {
        this.clubList = clubList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return clubList.size
    }
}