package com.egoriku.grodnoroads.guidance.data.repository

import com.egoriku.grodnoroads.extensions.common.ResultOf
import com.egoriku.grodnoroads.guidance.data.dto.UserCountDTO
import com.egoriku.grodnoroads.guidance.domain.repository.UserCountRepository
import dev.gitlive.firebase.database.DatabaseReference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

internal class UserCountRepositoryImpl(
    private val databaseReference: DatabaseReference
) : UserCountRepository {

    override fun loadAsFlow(): Flow<ResultOf.Success<Int>> = databaseReference
        .child("users_count")
        .valueEvents
        .map {
            ResultOf.Success(it.value<UserCountDTO>().count)
        }
        .catch { ResultOf.Failure(it) }
        .flowOn(Dispatchers.IO)
}