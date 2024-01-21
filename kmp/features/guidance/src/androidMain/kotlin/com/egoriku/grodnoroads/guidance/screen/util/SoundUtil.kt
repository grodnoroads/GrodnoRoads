package com.egoriku.grodnoroads.guidance.screen.util

import android.content.Context
import com.egoriku.grodnoroads.audioplayer.AudioPlayer
import com.egoriku.grodnoroads.audioplayer.Sound
import com.egoriku.grodnoroads.extensions.DateTime
import com.egoriku.grodnoroads.guidance.domain.model.CameraType
import com.egoriku.grodnoroads.guidance.domain.model.MapEventType
import com.egoriku.grodnoroads.uuid.Uuid
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

class SoundUtil(context: Context) {

    private val audioPlayer = AudioPlayer(context)
    private val soundHistory = mutableMapOf<String, SoundTimeStamp>()

    private val overSpeedId = Uuid.randomUUID()

    private val currentTimeMillis: Long
        get() = DateTime.currentTimeMillis()

    fun playOverSpeed() = playSound(
        sound = Sound.OverSpeed,
        id = overSpeedId,
        expiration = FIVE_SECONDS
    )

    fun playIncident(id: String, mapEventType: MapEventType) {
        val incidentSound = when (mapEventType) {
            MapEventType.TrafficPolice -> Sound.TrafficPolice
            MapEventType.RoadIncident -> Sound.RoadIncident
            MapEventType.CarCrash -> Sound.CarCrash
            MapEventType.TrafficJam -> Sound.TrafficJam
            MapEventType.WildAnimals -> Sound.WildAnimals
            MapEventType.Unsupported -> null
        } ?: return

        val isSkipByDuplicate = checkDuplicate(incidentSound)

        when {
            isSkipByDuplicate -> return
            else -> playSound(sound = incidentSound, id = id)
        }
    }

    fun playCameraLimit(id: String, speedLimit: Int, cameraType: CameraType) {
        val speedLimitSound = when (speedLimit) {
            40 -> Sound.SpeedLimit40
            50 -> Sound.SpeedLimit50
            60 -> Sound.SpeedLimit60
            70 -> Sound.SpeedLimit70
            80 -> Sound.SpeedLimit80
            90 -> Sound.SpeedLimit90
            100 -> Sound.SpeedLimit100
            110 -> Sound.SpeedLimit110
            120 -> Sound.SpeedLimit120
            else -> null
        }
        val cameraSound = when (cameraType) {
            CameraType.StationaryCamera -> Sound.StationaryCamera
            CameraType.MobileCamera -> Sound.MobileCamera
            CameraType.MediumSpeedCamera -> Sound.MediumSpeedCamera
        }

        val isSkipByDuplicate = checkDuplicate(cameraSound)
        when {
            isSkipByDuplicate -> return
            else -> {
                playSound(sound = cameraSound, id = id)
                speedLimitSound?.run { playSound(sound = this, id = "camera_$id") }
            }
        }
    }

    private fun checkDuplicate(sound: Sound, expiration: Long = ONE_MINUTE): Boolean {
        val currentTimeMillis = currentTimeMillis
        return soundHistory
            .filterValues { soundTimeStamp ->
                soundTimeStamp.sound == sound &&
                        soundTimeStamp.timestamp >= currentTimeMillis - expiration
            }
            .isNotEmpty()
    }

    fun setVolume(level: Float) = audioPlayer.setVolumeLevel(level)
    fun setLoudness(loudness: Int) = audioPlayer.setLoudness(loudness)

    private fun playSound(
        sound: Sound,
        id: String = Uuid.randomUUID().toString(),
        expiration: Long = FIVE_MINUTE
    ) {
        val currentTimeMillis = currentTimeMillis
        val item = soundHistory[id]

        if (item == null) {
            soundHistory[id] = SoundTimeStamp(sound = sound, timestamp = currentTimeMillis)
            audioPlayer.enqueueSound(sound)
        } else if (currentTimeMillis - item.timestamp > expiration) {
            soundHistory[id] = item.copy(timestamp = currentTimeMillis)
            audioPlayer.enqueueSound(sound)
        }

        invalidateOldSounds()
    }

    private fun invalidateOldSounds() {
        val currentTime = currentTimeMillis
        val invalidIds = soundHistory
            .filterValues { currentTime - it.timestamp > THIRTY_MINUTES }
            .keys

        invalidIds.forEach(soundHistory::remove)
    }

    private data class SoundTimeStamp(
        val sound: Sound,
        val timestamp: Long
    )

    companion object {
        val FIVE_SECONDS = 5.seconds.inWholeMilliseconds
        val ONE_MINUTE = 30.seconds.inWholeMilliseconds
        val FIVE_MINUTE = 5.minutes.inWholeMilliseconds
        val THIRTY_MINUTES = 30.minutes.inWholeMilliseconds
    }
}