package com.egoriku.grodnoroads.guidance.screen.di

import com.egoriku.grodnoroads.guidance.screen.util.MarkerCache
import com.egoriku.grodnoroads.guidance.screen.util.SoundUtil
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val guidanceUiModule = module {
    singleOf(::MarkerCache)
    singleOf(::SoundUtil)
}