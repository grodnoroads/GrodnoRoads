package com.egoriku.grodnoroads.guidance.screen.util

import android.content.Context
import com.egoriku.grodnoroads.compose.snackbar.model.MessageData.Resource
import com.egoriku.grodnoroads.compose.snackbar.model.SnackbarMessage
import com.egoriku.grodnoroads.compose.snackbar.model.SnackbarMessage.ActionMessage
import com.egoriku.grodnoroads.compose.snackbar.model.SnackbarMessage.SimpleMessage
import com.egoriku.grodnoroads.extensions.openAppSettings
import com.egoriku.grodnoroads.location.requester.LocationRequestStatus
import com.egoriku.grodnoroads.location.requester.LocationRequestStatus.*
import com.egoriku.grodnoroads.resources.R

class SnackbarMessageBuilder(private val context: Context) {

    fun handleDriveModeRequest(status: LocationRequestStatus): SnackbarMessage? = when (status) {
        ShowRationale -> SimpleMessage(
            title = Resource(R.string.snackbar_drive_mode_rationale_title),
            description = Resource(R.string.snackbar_drive_mode_rationale_description)
        )
        FineLocationDenied -> SimpleMessage(
            title = Resource(R.string.snackbar_drive_mode_fine_location_denied)
        )
        PermissionDenied -> ActionMessage(
            title = Resource(R.string.snackbar_drive_mode_permission_denied),
            onAction = {
                context.openAppSettings()
            }
        )
        GmsLocationDisabled -> SimpleMessage(
            title = Resource(R.string.snackbar_drive_mode_gms_disabled_title),
            description = Resource(R.string.snackbar_drive_mode_gms_disabled_description)
        )
        GmsLocationEnabled -> null
    }

    fun handleCurrentLocationRequest(status: LocationRequestStatus): SnackbarMessage? =
        when (status) {
            ShowRationale -> SimpleMessage(title = Resource(R.string.snackbar_current_location_rationale_title))
            FineLocationDenied -> SimpleMessage(title = Resource(R.string.snackbar_current_location_fine_location_denied))
            PermissionDenied -> ActionMessage(
                title = Resource(R.string.snackbar_current_location_permission_denied),
                onAction = {
                    context.openAppSettings()
                }
            )
            GmsLocationDisabled -> SimpleMessage(
                title = Resource(R.string.snackbar_current_location_gms_disabled_title),
                description = Resource(R.string.snackbar_current_location_gms_disabled_description)
            )
            GmsLocationEnabled -> null
        }
}