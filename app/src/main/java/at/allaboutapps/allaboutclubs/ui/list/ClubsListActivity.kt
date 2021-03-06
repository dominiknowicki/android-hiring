package at.allaboutapps.allaboutclubs.ui.list

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import at.allaboutapps.allaboutclubs.AppImpl
import at.allaboutapps.allaboutclubs.R
import at.allaboutapps.allaboutclubs.api.DataModel
import at.allaboutapps.allaboutclubs.api.models.Club
import at.allaboutapps.allaboutclubs.ui.customviews.CustomToast
import at.allaboutapps.allaboutclubs.ui.details.DetailsActivity
import at.allaboutapps.allaboutclubs.ui.list.clublistrecycler.ClubListAdapter
import at.allaboutapps.allaboutclubs.ui.list.clublistrecycler.ClubListRecyclerCallback
import at.allaboutapps.allaboutclubs.utils.SortOrder
import at.allaboutapps.allaboutclubs.utils.setHorizontalDivider
import kotlinx.android.synthetic.main.activity_clubs_list.*
import java.util.*

class ClubsListActivity : AppCompatActivity(), ClubListRecyclerCallback {

    private lateinit var viewModel: ClubsListViewModel
    private val customToast: CustomToast by lazy {
        CustomToast(this@ClubsListActivity)
    }

    private val clubListAdapter by lazy {
        ClubListAdapter(LinkedList(), this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        this.overridePendingTransition(0, 0)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clubs_list)
        setSupportActionBar(findViewById(R.id.myToolbar))
        initRecycler()
        initViewModel()
        initOnClicks()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.menuSort -> {
                viewModel.switchSortOrder()
                if (viewModel.getSortOrder() == SortOrder.ASC)
                    menuItem.setIcon(R.drawable.ic_filter_rotated)
                else
                    menuItem.setIcon(R.drawable.ic_filter)
                return true
            }
        }
        return super.onOptionsItemSelected(menuItem)
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        val menuSort = menu.findItem(R.id.menuSort)
        if (viewModel.getSortOrder() == SortOrder.ASC)
            menuSort.setIcon(R.drawable.ic_filter_rotated)
        else
            menuSort.setIcon(R.drawable.ic_filter)
        return super.onPrepareOptionsMenu(menu)
    }

    // implements ClubListRecyclerCallback
    override fun onClick(club: Club) {
        DetailsActivity.startActivity(this@ClubsListActivity, club)
    }

    private fun initRecycler() {
        clubsListRecyclerView.adapter = clubListAdapter
        val layoutManager = LinearLayoutManager(this@ClubsListActivity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        clubsListRecyclerView.layoutManager = layoutManager
        clubsListRecyclerView.setHorizontalDivider(R.drawable.divider)
    }

    private fun initViewModel() {
        val dataModel: DataModel = (application as AppImpl).dataModel
        viewModel = ViewModelProviders.of(this@ClubsListActivity, ClubsListViewModelFactory(dataModel)).get(ClubsListViewModel::class.java)
        viewModel.getClubsList().observe(this@ClubsListActivity, android.arch.lifecycle.Observer { clubList ->
            clubList?.let {
                clubListAdapter.setList(it)
                clubsListSwipeRefreshLayout.isRefreshing = false
            }
        })
        viewModel.getError().observe(this@ClubsListActivity, android.arch.lifecycle.Observer { message ->
            message?.let {
                customToast.show(it)
                viewModel.resetError()
                clubsListSwipeRefreshLayout.isRefreshing = false
            }
        })
        clubsListSwipeRefreshLayout.isRefreshing = true
        viewModel.refresh(false)
    }

    private fun initOnClicks() {
        clubsListSwipeRefreshLayout.setOnRefreshListener {
            viewModel.refresh(true)
        }
    }
}
