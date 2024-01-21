package com.egoriku.grodnoroads.guidance.domain.repository

import com.egoriku.grodnoroads.extensions.common.ResultOf
import com.egoriku.grodnoroads.guidance.domain.model.MapEvent.Camera.MediumSpeedCamera
import kotlinx.coroutines.flow.Flow

interface MediumSpeedCameraRepository {

    fun loadAsFlow(): Flow<ResultOf<List<MediumSpeedCamera>>>
}