package at.allaboutapps.a3hiring.ui.list

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import at.allaboutapps.a3hiring.AppImpl
import at.allaboutapps.a3hiring.R
import at.allaboutapps.a3hiring.api.models.Club
import at.allaboutapps.a3hiring.api.retrofit.DEBUG_TAG
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import java.util.*

class ListActivity : AppCompatActivity() {

    val disposable: CompositeDisposable by lazy {
        CompositeDisposable()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        this.overridePendingTransition(0, 0)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        downloadData()
    }

    override fun onDestroy() {
        disposable.clear()
        super.onDestroy()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        //TODO: handle sort item click
        return super.onOptionsItemSelected(menuItem)
    }

    /**
     * Implements MainView
     */
    private var list: LinkedList<Club>? = null

    private fun downloadData() {
        val apiInterface = (application as AppImpl).apiInterface
        disposable.add(apiInterface.getClubList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<LinkedList<Club>>() {
                    override fun onSuccess(t: LinkedList<Club>) {
                        list = t
                    }

                    override fun onError(e: Throwable) {
                        Log.e(DEBUG_TAG, e.toString())
                    }
                }))
    }
}
