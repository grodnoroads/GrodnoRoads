package com.egoriku.grodnoroads.guidance.data.repository

import com.egoriku.grodnoroads.extensions.common.ResultOf
import com.egoriku.grodnoroads.guidance.data.dto.StationaryDTO
import com.egoriku.grodnoroads.guidance.domain.model.MapEvent.Camera.StationaryCamera
import com.egoriku.grodnoroads.guidance.domain.repository.StationaryCameraRepository
import com.egoriku.grodnoroads.location.LatLng
import dev.gitlive.firebase.database.DatabaseReference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

internal class StationaryCameraRepositoryImpl(
    private val databaseReference: DatabaseReference
) : StationaryCameraRepository {

    override fun loadAsFlow(): Flow<ResultOf<List<StationaryCamera>>> {
        return databaseReference
            .child("v2/stationary_cameras")
            .valueEvents
            .map { snapshot ->
                ResultOf.Success(
                    snapshot.children
                        .map { it.value<StationaryDTO>() }
                        .map { data ->
                            StationaryCamera(
                                id = data.id,
                                name = data.name,
                                angle = data.angle,
                                bidirectional = data.bidirectional,
                                updateTime = data.updateTime,
                                speedCar = data.speedCar,
                                speedTruck = data.speedTruck,
                                position = LatLng(data.latitude, data.longitude)
                            )
                        }
                )
            }
            .catch { ResultOf.Failure(it) }
            .flowOn(Dispatchers.IO)
    }
}