package com.egoriku.grodnoroads.settings.appearance.domain.util

import com.egoriku.grodnoroads.shared.persistent.appearance.Language
import platform.Foundation.NSLocale
import platform.Foundation.currentLocale
import platform.Foundation.languageCode

actual fun isBYLocaleSupported() = false

actual fun resetAppLanguage() {
    error("Should not be called from iOS, use System settings")
}

actual fun setAppLanguage(language: String) {
    error("Should not be called from iOS, use System settings")
}

actual fun getCurrentLanguage() = Language.localeToLanguage(NSLocale.currentLocale.languageCode)