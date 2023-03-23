package org.aaaid.eservices.network.repository

import org.aaaid.eservices.R
import org.aaaid.eservices.base.BaseApplication
import org.aaaid.eservices.network.ApiService
import org.aaaid.eservices.network.Resource
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun getUser(restaurantBranchId: Int): Resource<List<Any>> {
        return try {
            val response = apiService.getUser(restaurantBranchId)
            Resource.Success(response)
        } catch (e: Exception) {
            Resource.Error(e.message ?: BaseApplication.getLocalizedResources(R.string.err_msg_unknown_error_occured))
        }
    }
}