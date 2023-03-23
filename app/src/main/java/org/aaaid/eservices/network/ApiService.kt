package org.aaaid.eservices.network

import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET(ApiURL.URL_GET_DEMO_DATA)
    suspend fun getUser(@Query("restaurantBranchId") restaurantBranchId: Int): List<Any>
}