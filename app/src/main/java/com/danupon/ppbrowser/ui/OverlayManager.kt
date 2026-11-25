package com.danupon.ppbrowser.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.widget.Toast
import android.graphics.Bitmap
import android.graphics.Color
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import com.danupon.ppbrowser.AppConstants
import com.danupon.ppbrowser.BuildConfig
import com.danupon.ppbrowser.R
import com.danupon.ppbrowser.bookmarks.BookmarkManager
import com.danupon.ppbrowser.data.BrowserPreferences
import com.danupon.ppbrowser.databinding.ActivityMainBinding
import com.danupon.ppbrowser.settings.SettingsCallbacks
import com.danupon.ppbrowser.settings.SettingsViews
import com.danupon.ppbrowser.startpage.StartPageManager
import com.danupon.ppbrowser.tabs.TabManager
import com.danupon.ppbrowser.web.updatePageDarkening

class OverlayManager(
    private val activity: AppCompatActivity,
    private val binding: ActivityMainBinding,
    private val tabManager: TabManager,
    private val bookmarkManager: BookmarkManager,
    private val startPageManager: StartPageManager,
    private val uiManager: BrowserUIManager,
    private val callbacks: OverlayCallbacks
) {

    interface OverlayCallbacks {
        fun onRecreateRequested()
        fun onHomePageChanged()
        fun onPickBackgroundRequested()
    }

    fun showQrCodeView(url: String) {
        if (url.isBlank()) {
            return
        }

        hideAllOverlays()
        binding.qrCodeViewRoot.visibility = View.VISIBLE
        binding.qrCodeImage.setImageBitmap(null)
        binding.qrCodeUrl.text = url

        uiManager.applySheetHeightCap()

        activity.lifecycleScope.launch {
            val bitmap = QRUtils.generateQrCodeAsync(url)
            if (bitmap != null) {
                binding.qrCodeImage.setImageBitmap(bitmap)
            }
            uiManager.applySheetHeightCap()
        }
    }

    fun hideQrCodeView() {
        binding.qrCodeViewRoot.visibility = View.GONE
        binding.menuScroll.visibility = View.VISIBLE
        uiManager.applySheetHeightCap()
    }

    fun showSettingsView() {
        hideAllOverlays()
        binding.settingsViewRoot.visibility = View.VISIBLE
        ensureSettingsContentPopulated()
        uiManager.applySheetHeightCap()
    }

    fun hideSettingsView() {
        binding.settingsViewRoot.visibility = View.GONE
        binding.menuScroll.visibility = View.VISIBLE
        uiManager.applySheetHeightCap()
    }

    fun showCheckLatestView() {
        hideAllOverlays()
        binding.checkLatestViewRoot.visibility = View.VISIBLE
        // Updates come from Google Play, so there is nothing to fetch here.
        binding.checkLatestProgressIndicator.visibility = View.GONE

        binding.checkLatestLatestVersion.setText(R.string.check_latest_play_store)
        binding.checkLatestLatestVersion.setTextColor(getColorFromAttr(android.R.attr.textColorPrimary))

        val packageName = activity.packageName
        binding.checkLatestInstalledVersion.text = activity.getString(
            R.string.installed_version_label,
            "v${com.danupon.ppbrowser.BuildConfig.VERSION_NAME}"
        )

        uiManager.applySheetHeightCap()
    }

    fun hideCheckLatestView() {
        binding.checkLatestViewRoot.visibility = View.GONE
        binding.menuScroll.visibility = View.VISIBLE
        uiManager.applySheetHeightCap()
    }

    /**
     * Only one of these is ever on screen at a time; they share the sheet, so hiding the rest
     * is what lets [BrowserUIManager.applySheetHeightCap] size the sheet to the visible one.
     */
    private fun hideAllOverlays() {
        val views = listOf(
            binding.menuScroll,
            binding.bookmarkManagerRoot,
            binding.tabManagerRoot,
            binding.checkLatestViewRoot,
            binding.settingsViewRoot,
            binding.qrCodeViewRoot
        )
        views.forEach {
            it.visibility = View.GONE
        }
    }

    private fun ensureSettingsContentPopulated() {
        if (binding.settingsContentContainer.childCount > 0) {
            return
        }

        try {
            val settingsCallbacks = SettingsCallbacks(
                onClose = {
                    hideSettingsView()
                },
                onThemeChanged = {
                    callbacks.onRecreateRequested()
                },
                onPageDarkeningChanged = {
                    val enabled = BrowserPreferences.isBetaForceDarkPagesEnabled(activity)
                    tabManager.browserTabs.forEach { tab ->
                        tab.webView.updatePageDarkening(enabled)
                    }
                },
                onScaleChanged = {
                    callbacks.onRecreateRequested()
                },
                onHomePageChanged = {
                    callbacks.onHomePageChanged()
                },
                onPickStartPageBackground = {
                    callbacks.onPickBackgroundRequested()
                },
                onClearStartPageBackground = {
                    startPageManager.clearStartPageBackground()
                },
                onShieldsChanged = {
                    tabManager.browserTabs.forEach { tab ->
                        tab.webView.reload()
                    }
                }
            )

            val contentView = SettingsViews.createSettingsContent(activity, false, settingsCallbacks)
            binding.settingsContentContainer.addView(contentView)
        } catch (e: Exception) {
            // Log or handle error
        }
    }

    /** Opens this app's Google Play page, where updates are installed. */
    fun onReleaseButtonClicked() {
        val pkg = activity.packageName
        val opened = runCatching {
            activity.startActivity(
                Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$pkg"))
                    .setPackage("com.android.vending")
            )
        }.isSuccess
        if (!opened) {
            uiManager.openUriExternally(Uri.parse("https://play.google.com/store/apps/details?id=$pkg"))
        }
    }

    private fun getColorFromAttr(attrResId: Int): Int {
        val tv = android.util.TypedValue()
        if (activity.theme.resolveAttribute(attrResId, tv, true)) {
            if (tv.resourceId != 0) {
                return androidx.core.content.ContextCompat.getColor(activity, tv.resourceId)
            }
            return tv.data
        }
        return android.graphics.Color.TRANSPARENT
    }
}
