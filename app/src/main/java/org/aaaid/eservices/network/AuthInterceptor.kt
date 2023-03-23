package org.aaaid.eservices.network

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import okhttp3.Response
import org.aaaid.eservices.preferences.PreferencesManager
import org.aaaid.eservices.utils.Constants.Keys.KEY_AUTHENTICATION_TOKEN
import javax.inject.Inject

class AuthInterceptor @Inject constructor(@ApplicationContext private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val token : String? = PreferencesManager(context).getString(KEY_AUTHENTICATION_TOKEN, null)
        token?.let {
            request = request.newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
        }
        return chain.proceed(request)
    }
}