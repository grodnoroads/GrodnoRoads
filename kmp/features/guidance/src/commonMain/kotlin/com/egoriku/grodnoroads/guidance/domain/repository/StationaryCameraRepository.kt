package com.egoriku.grodnoroads.guidance.domain.repository

import com.egoriku.grodnoroads.extensions.common.ResultOf
import com.egoriku.grodnoroads.guidance.domain.model.MapEvent.Camera.StationaryCamera
import kotlinx.coroutines.flow.Flow

interface StationaryCameraRepository {

    fun loadAsFlow(): Flow<ResultOf<List<StationaryCamera>>>
}