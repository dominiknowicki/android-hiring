package at.allaboutapps.allaboutclubs.ui.details

import android.app.Activity
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.style.StyleSpan
import at.allaboutapps.allaboutclubs.R
import at.allaboutapps.allaboutclubs.api.models.Club
import at.allaboutapps.allaboutclubs.utils.loadWithGlide
import at.allaboutapps.allaboutclubs.utils.setTextWithSpan
import kotlinx.android.synthetic.main.activity_club_details.*


class DetailsActivity : AppCompatActivity() {

    private lateinit var club: Club

    companion object {
        private const val CLUB_MODEL = "CLUB_MODEL"
        fun startActivity(activity: Activity, club: Club) {
            val intent = Intent(activity, DetailsActivity::class.java)
            intent.putExtra(CLUB_MODEL, club)
            activity.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        overridePendingTransition(R.anim.slide_in_right, R.anim.stay)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_club_details)
    }

    override fun onResume() {
        super.onResume()
        club = intent.getParcelableExtra(CLUB_MODEL)
        initOnClicks()
        fillWithData()
    }

    override fun finish() {
        super.finish()
        this.overridePendingTransition(R.anim.stay, R.anim.slide_out_right)
    }

    private fun fillWithData() {
        actionbarTextTV.text = club.name
        countryTV.text = club.country
        imageIV.loadWithGlide(club.image)
        val detailText = getString(R.string.club_from_is_worth_mln_euro, club.name, club.country, club.value.toInt())
        detailsTV.setTextWithSpan(detailText, club.name, StyleSpan(Typeface.BOLD))
    }

    private fun initOnClicks() {
        actionbarBackArrowIV.setOnClickListener { finish() }
    }
}
