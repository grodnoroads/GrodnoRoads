package com.egoriku.grodnoroads.guidance.data.repository

import com.egoriku.grodnoroads.guidance.data.dto.areas.AreasDTO
import com.egoriku.grodnoroads.guidance.data.util.AreasGeoJsonAssetReader
import com.egoriku.grodnoroads.guidance.domain.repository.CityAreasRepository
import com.egoriku.grodnoroads.logger.logD
import kotlinx.serialization.json.Json

class CityAreasRepositoryImpl : CityAreasRepository {

    private val json = Json { ignoreUnknownKeys = true }

    override suspend fun load() {
        val areas = AreasGeoJsonAssetReader.readAreasFile()

        val featureCollection = json.decodeFromString<AreasDTO>(areas)

        logD(featureCollection.toString())
    }
}