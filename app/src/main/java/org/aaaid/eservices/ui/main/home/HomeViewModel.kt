package org.aaaid.eservices.ui.main.home

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.aaaid.eservices.R
import org.aaaid.eservices.base.BaseApplication
import org.aaaid.eservices.enum.NetworkErrorType
import org.aaaid.eservices.network.Resource
import org.aaaid.eservices.network.filterResource
import org.aaaid.eservices.network.repository.UserRepository
import org.aaaid.eservices.utils.isInternetConnected
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val application: Application
) : ViewModel() {

    private val _demoData = MutableLiveData<Resource<List<Any>>>()
    val demoData: LiveData<Resource<List<Any>>> = _demoData

    fun getUser(restaurantBranchId: Int) {
        viewModelScope.launch {
            _demoData.value = Resource.Loading
            if (!isInternetConnected(application.applicationContext)) {
                _demoData.value =
                    Resource.Error(
                        BaseApplication.getLocalizedResources(R.string.err_msg_internet_not_connected),
                        NetworkErrorType.NO_INTERNET
                    )
            } else {
                val response = userRepository.getUser(restaurantBranchId)
                _demoData.value = filterResource(response)
            }

        }


    }
}