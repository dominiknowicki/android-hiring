package at.allaboutapps.allaboutclubs.ui.list

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import at.allaboutapps.allaboutclubs.api.DataModel


@Suppress("UNCHECKED_CAST")
class ClubsListViewModelFactory(private var dataModel: DataModel) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ClubsListViewModel(dataModel) as T
    }
}