package org.aaaid.eservices.base

import android.content.Context
import android.content.ContextWrapper
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import java.util.*


open class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        if (BaseApplication.isEnglishSelected())
        {
            window.decorView.layoutDirection = View.LAYOUT_DIRECTION_LTR
        }else{
            window.decorView.layoutDirection = View.LAYOUT_DIRECTION_RTL
        }
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(ContextWrapper(newBase.setAppLocale(BaseApplication.getCurrentLanguage())))
    }

    private fun Context.setAppLocale(language: String): Context {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val config = resources.configuration
        config.setLocale(locale)
        config.setLayoutDirection(locale)
        return createConfigurationContext(config)
    }
}

