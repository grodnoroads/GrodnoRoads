package com.egoriku.grodnoroads.guidance.data.repository

import com.egoriku.grodnoroads.extensions.common.ResultOf
import com.egoriku.grodnoroads.guidance.data.dto.ReportsDTO
import com.egoriku.grodnoroads.guidance.data.mapper.ReportsActionMapper
import com.egoriku.grodnoroads.guidance.data.mapper.ReportsMapper
import com.egoriku.grodnoroads.guidance.domain.model.report.ReportActionModel
import com.egoriku.grodnoroads.guidance.domain.repository.ReportsRepository
import dev.gitlive.firebase.database.DatabaseReference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

internal class ReportsRepositoryImpl(databaseReference: DatabaseReference) : ReportsRepository {

    private val reportsReference = databaseReference.child("reports")

    override fun loadAsFlow() = reportsReference
        .orderByChild("timestamp")
        .valueEvents
        .map { snapshot ->
            val dtoList = snapshot.children.map { it.value<ReportsDTO>() }
            ResultOf.Success(ReportsMapper(dtoList))
        }
        .catch { ResultOf.Failure(it) }
        .flowOn(Dispatchers.IO)

    override suspend fun report(actionModel: ReportActionModel) {
        reportsReference
            .push()
            .setValue(ReportsActionMapper(actionModel))
    }
}