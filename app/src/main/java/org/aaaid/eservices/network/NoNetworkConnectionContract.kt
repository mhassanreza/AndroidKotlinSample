package org.aaaid.eservices.network

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import org.aaaid.eservices.ui.main.errors.NoInternetConnectionActivity

class NoNetworkConnectionContract : ActivityResultContract<Unit?, Boolean>() {

    override fun createIntent(context: Context, input: Unit?): Intent {
        return Intent(context, NoInternetConnectionActivity::class.java)
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Boolean {
        return resultCode == Activity.RESULT_OK
    }
}