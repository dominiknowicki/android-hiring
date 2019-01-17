package at.allaboutapps.allaboutclubs.ui.list.clublistrecycler

import at.allaboutapps.allaboutclubs.api.models.Club

interface ClubListRecyclerCallback {

    fun onClick(club: Club)
}