package com.danupon.ppbrowser

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.danupon.ppbrowser.data.BrowserPreferences
import com.danupon.ppbrowser.data.CrashLog

class PPBrowserApp : Application() {
    override fun onCreate() {
        super.onCreate()
        CrashLog.install(this)
        // MainActivity handles uiMode changes itself (configChanges) so it is never recreated when
        // the night mode flips; the mode therefore has to be decided before the first activity
        // inflates, otherwise a system-dark phone shows the light theme with dark widgets.
        AppCompatDelegate.setDefaultNightMode(BrowserPreferences.getThemeMode(this).nightMode)
    }
}
