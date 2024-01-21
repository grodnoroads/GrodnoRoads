package com.egoriku.grodnoroads.guidance.domain.repository

import com.egoriku.grodnoroads.extensions.common.ResultOf
import kotlinx.coroutines.flow.Flow

interface UserCountRepository {

    fun loadAsFlow(): Flow<ResultOf<Int>>
}