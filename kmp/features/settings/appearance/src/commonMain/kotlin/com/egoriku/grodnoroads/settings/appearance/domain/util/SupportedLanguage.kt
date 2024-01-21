package com.egoriku.grodnoroads.settings.appearance.domain.util

import com.egoriku.grodnoroads.shared.persistent.appearance.Language

expect fun isBYLocaleSupported(): Boolean

expect fun resetAppLanguage()

expect fun setAppLanguage(language: String)

expect fun getCurrentLanguage(): Language