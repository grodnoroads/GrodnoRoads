package com.egoriku.grodnoroads.guidance.data.repository

import com.egoriku.grodnoroads.extensions.common.ResultOf
import com.egoriku.grodnoroads.extensions.common.ResultOf.Success
import com.egoriku.grodnoroads.guidance.data.dto.MediumSpeedDTO
import com.egoriku.grodnoroads.guidance.domain.model.MapEvent.Camera.MediumSpeedCamera
import com.egoriku.grodnoroads.guidance.domain.repository.MediumSpeedCameraRepository
import com.egoriku.grodnoroads.location.LatLng
import dev.gitlive.firebase.database.DatabaseReference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

internal class MediumSpeedCameraRepositoryImpl(
    private val databaseReference: DatabaseReference
) : MediumSpeedCameraRepository {

    override fun loadAsFlow(): Flow<ResultOf<List<MediumSpeedCamera>>> {
        return databaseReference
            .child("v2/medium_speed_cameras")
            .valueEvents
            .map { snapshot ->
                Success(
                    snapshot.children
                        .map { it.value<MediumSpeedDTO>() }
                        .map { data ->
                            MediumSpeedCamera(
                                id = data.id,
                                name = data.name,
                                angle = data.angle,
                                bidirectional = data.bidirectional,
                                updateTime = data.updateTime,
                                speedCar = data.speedCar,
                                speedTruck = data.speedTruck,
                                position = LatLng(data.latitude, data.longitude)
                            )
                        })
            }
            .catch { ResultOf.Failure(it) }
            .flowOn(Dispatchers.IO)
    }
}