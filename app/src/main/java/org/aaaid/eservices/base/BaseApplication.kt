package org.aaaid.eservices.base

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import dagger.hilt.android.HiltAndroidApp
import org.aaaid.eservices.preferences.PreferencesManager
import org.aaaid.eservices.utils.Constants.Keys.KEY_IS_FIRST_TIME_LANGUAGE_SELECT
import org.aaaid.eservices.utils.Constants.Keys.KEY_PREFERENCES_LANGUAGE
import org.aaaid.eservices.utils.Constants.Keys.LANGUAGE_ARABIC
import org.aaaid.eservices.utils.Constants.Keys.LANGUAGE_ENGLISH
import java.util.*

@HiltAndroidApp
class BaseApplication : Application() {
    companion object {
        private lateinit var preferencesManager: PreferencesManager
        private lateinit var mContext: BaseApplication
        private lateinit var language: String

        fun getInstance(): BaseApplication {
            return mContext
        }

        fun isEnglishSelected(): Boolean {
            return language == LANGUAGE_ENGLISH
        }

        fun updatePreferencesData() {
            val selectedLanguage: String? =
                preferencesManager.getString(KEY_PREFERENCES_LANGUAGE, LANGUAGE_ENGLISH)
            language = selectedLanguage!!
        }

        fun getCurrentLanguage(): String {
            return if (this::language.isInitialized) {
                language
            } else {
                LANGUAGE_ENGLISH
            }
        }

        fun getLocalizedResources(id: Int): String {
            var conf: Configuration = mContext.getResources().getConfiguration()
            conf = Configuration(conf)
            conf.setLocale(
                if (isEnglishSelected()) Locale(LANGUAGE_ENGLISH) else Locale(
                    LANGUAGE_ARABIC
                )
            )
            val localizedContext: Context = mContext.createConfigurationContext(conf)
            return localizedContext.resources.getString(id)
        }
    }


    override fun onCreate() {
        super.onCreate()
        mContext = this
        preferencesManager = PreferencesManager(mContext)
        if (!preferencesManager.getBoolean(KEY_IS_FIRST_TIME_LANGUAGE_SELECT, false)) {
            if (Locale.getDefault().language.equals(LANGUAGE_ENGLISH)) {
                preferencesManager.setString(KEY_PREFERENCES_LANGUAGE, LANGUAGE_ARABIC)
            } else if (Locale.getDefault().language.equals(LANGUAGE_ARABIC)) {
                preferencesManager.setString(KEY_PREFERENCES_LANGUAGE, LANGUAGE_ARABIC)
            }
            preferencesManager.setBoolean(KEY_IS_FIRST_TIME_LANGUAGE_SELECT, true)
        }
        updatePreferencesData()
    }


//
//
//    fun getStringFromResources(id: Int): String? {
//        return BaseApplication.mContext.resources.getString(id)
//    }

    //
//    fun getColorFromResource(colorId: Int): Int {
//        return BaseApplication.mContext.getResources()
//            .getColor(colorId, BaseApplication.mContext.getResources().newTheme())
//    }
//

//
//    fun getLocalizedResources(id: Int, vararg value: Any?): String {
//        var conf: Configuration = BaseApplication.mContext.getResources().getConfiguration()
//        conf = Configuration(conf)
//        conf.setLocale(if (isEnglishSelected()) Locale(LANGUAGE_ENGLISH) else Locale(LANGUAGE_ARABIC))
//        val localizedContext: Context = BaseApplication.mContext.createConfigurationContext(conf)
//        return String.format(localizedContext.resources.getString(id), *value)
//    }
//
//    fun getLocalizedDrawableResources(id: Int): Drawable {
//        var conf: Configuration = BaseApplication.mContext.getResources().getConfiguration()
//        conf = Configuration(conf)
//        conf.setLocale(if (isEnglishSelected()) Locale(LANGUAGE_ENGLISH) else Locale(LANGUAGE_ARABIC))
//        val localizedContext: Context = BaseApplication.mContext.createConfigurationContext(conf)
//        return localizedContext.resources.getDrawable(id)
//    }
}