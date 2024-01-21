package com.egoriku.grodnoroads.settings.faq.domain.repository

import com.egoriku.grodnoroads.extensions.common.ResultOf
import com.egoriku.grodnoroads.settings.faq.domain.model.FAQ

internal interface FaqRepository {

    suspend fun load(): ResultOf<List<FAQ>>
}