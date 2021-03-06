package at.allaboutapps.allaboutclubs.ui.list

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import at.allaboutapps.allaboutclubs.api.DataModel
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

    private lateinit var dataModel: DataModel
    private lateinit var clubsList: MutableLiveData<LinkedList<Club>>
    private lateinit var error: MutableLiveData<String>
    private var sortOrder = SortOrder.ASC
    private var disposable: Disposable? = null

    constructor(dataModel: DataModel) : this() {
        this.dataModel = dataModel
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

    fun getSortOrder(): SortOrder {
        return sortOrder
    }

    fun switchSortOrder() {
        sortOrder = sortOrder.switch()
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

        disposable = dataModel.fetchClubsList(forceFetch)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally { disposable = null }
                .subscribeBy(
                        onSuccess = {
                            it.sort(sortOrder)
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