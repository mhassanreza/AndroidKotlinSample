package org.aaaid.eservices.ui.entry

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import org.aaaid.eservices.BuildConfig
import org.aaaid.eservices.R
import org.aaaid.eservices.base.BaseActivity
import org.aaaid.eservices.base.BaseApplication
import org.aaaid.eservices.databinding.ActivityEntryScreenBinding
import org.aaaid.eservices.glide.GlideApp
import org.aaaid.eservices.ui.main.MainActivity

class EntryScreenActivity : BaseActivity() {
    private lateinit var binding: ActivityEntryScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//         Enable support for Splash Screen API for proper Android 12+ support
        installSplashScreen()
        binding = ActivityEntryScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setIntroAnimation()
    }

    private fun setIntroAnimation() {
        with(binding) {
            val appVersion =
                "${BaseApplication.getLocalizedResources(R.string.version)} ${BuildConfig.VERSION_NAME}"
            tvVersion.text = appVersion
            GlideApp.with(ivSplash)
                .load(R.drawable.entry_screen_animation)
                .into(ivSplash)
        }

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this@EntryScreenActivity, MainActivity::class.java))
            finish()
        }, 9000)
    }


}