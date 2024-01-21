package com.egoriku.grodnoroads.guidance.domain.model.report

data class ReportActionModel(
    val type: String,
    val shortMessage: String,
    val message: String,
    val latitude: Double,
    val longitude: Double,
    val timestamp: Long,
    val source: String,
)