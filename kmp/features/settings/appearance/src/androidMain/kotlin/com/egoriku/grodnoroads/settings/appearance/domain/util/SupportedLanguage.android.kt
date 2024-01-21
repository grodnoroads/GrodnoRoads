package com.egoriku.grodnoroads.settings.appearance.domain.util

import android.annotation.SuppressLint
import android.os.Build
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import com.egoriku.grodnoroads.shared.persistent.appearance.Language

@SuppressLint("AnnotateVersionCheck")
actual fun isBYLocaleSupported() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.N

actual fun resetAppLanguage() =
    AppCompatDelegate.setApplicationLocales(LocaleListCompat.getEmptyLocaleList())

actual fun setAppLanguage(language: String) =
    AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(language))

actual fun getCurrentLanguage() = Language.localeToLanguage(currentAppLanguage)

private val currentAppLanguage: String?
    get() = AppCompatDelegate.getApplicationLocales()[0]?.language
