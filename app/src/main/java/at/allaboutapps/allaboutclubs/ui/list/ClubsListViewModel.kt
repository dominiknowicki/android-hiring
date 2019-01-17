package at.allaboutapps.allaboutclubs.ui.list

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import at.allaboutapps.allaboutclubs.api.DataSource
import at.allaboutapps.allaboutclubs.api.models.Club
import at.allaboutapps.allaboutclubs.utils.SortOrder
import at.allaboutapps.allaboutclubs.utils.sort
import at.allaboutapps.allaboutclubs.utils.switch
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import java.util.*

class ClubsListViewModel() : ViewModel() {

    private lateinit var dataSource: DataSource
    private lateinit var clubsList: MutableLiveData<LinkedList<Club>>
    private lateinit var error: MutableLiveData<String>
    private lateinit var sortOrder: MutableLiveData<SortOrder>
    private var disposable: Disposable? = null

    constructor(dataSource: DataSource) : this() {
        this.dataSource = dataSource
    }

    fun getClubsList(): MutableLiveData<LinkedList<Club>> {
        if (!::clubsList.isInitialized) {
            clubsList = MutableLiveData()
            clubsList.value = LinkedList()
        }
        return clubsList
    }

    fun getError(): MutableLiveData<String> {
        if (!::error.isInitialized) {
            error = MutableLiveData()
            error.value = null
        }
        return error
    }

    fun getSortOrder(): MutableLiveData<SortOrder> {
        if (!::sortOrder.isInitialized) {
            sortOrder = MutableLiveData()
            sortOrder.value = SortOrder.ASC
        }
        return sortOrder
    }

    fun switchSortOrder() {
        if (!::sortOrder.isInitialized) {
            sortOrder = MutableLiveData()
            sortOrder.value = SortOrder.ASC
        } else
            sortOrder.value = sortOrder.value?.switch()
        refresh(false)
    }

    override fun onCleared() {
        disposable?.dispose()
        resetViewModelData()
        super.onCleared()
    }

    fun refresh(forceFetch: Boolean) {
        // prevents multiple calls:
        if (disposable != null) return

        disposable = dataSource.fetchClubsList(forceFetch)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally { disposable = null }
                .subscribeBy(
                        onSuccess = {
                            it.sort(sortOrder.value)
                            clubsList.postValue(it)
                        },
                        onError = {
                            error.postValue(it.localizedMessage)
                        }
                )
    }

    fun resetError() {
        error.value = null
    }

    private fun resetViewModelData() {
        clubsList.value = null
        error.value = null
    }
}