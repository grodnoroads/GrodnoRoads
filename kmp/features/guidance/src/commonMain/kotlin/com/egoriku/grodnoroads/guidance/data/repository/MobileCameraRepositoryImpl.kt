package com.egoriku.grodnoroads.guidance.data.repository

import com.egoriku.grodnoroads.extensions.common.ResultOf
import com.egoriku.grodnoroads.extensions.common.ResultOf.Success
import com.egoriku.grodnoroads.guidance.data.dto.MobileCameraDTO
import com.egoriku.grodnoroads.guidance.domain.model.MapEvent
import com.egoriku.grodnoroads.guidance.domain.repository.MobileCameraRepository
import com.egoriku.grodnoroads.location.LatLng
import com.egoriku.grodnoroads.uuid.Uuid
import dev.gitlive.firebase.database.DatabaseReference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

internal class MobileCameraRepositoryImpl(
    private val databaseReference: DatabaseReference
) : MobileCameraRepository {

    override fun loadAsFlow() = databaseReference
        .child("temporary_camera/cameras")
        .valueEvents
        .map { resultOf ->
            Success(resultOf.value<List<MobileCameraDTO>>().map { data ->
                MapEvent.Camera.MobileCamera(
                    // TODO: use from backend
                    id = Uuid.randomUUID(),
                    name = data.name,
                    position = LatLng(data.latitude, data.longitude),
                    speedCar = data.speed,
                    speedTruck = data.speed,
                )
            })
        }
        .catch { ResultOf.Failure(it) }
        .flowOn(Dispatchers.IO)
}