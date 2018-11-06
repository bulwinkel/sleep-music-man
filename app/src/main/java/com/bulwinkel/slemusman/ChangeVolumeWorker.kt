package com.bulwinkel.slemusman

import android.content.Context
import android.media.AudioManager
import androidx.core.content.getSystemService
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import timber.log.Timber
import kotlin.math.abs

private var AudioManager.musicVolume: Int
    get() = getStreamVolume(AudioManager.STREAM_MUSIC)
    set(value) = setStreamVolume(AudioManager.STREAM_MUSIC, value, 0)

private val Int.seconds: Int get() = this * 1000
private val Int.minutes: Int get() = seconds * 60

class ChangeVolumeWorker(
    private val context : Context,
    private val params : WorkerParameters
) : Worker(context, params) {

    private val audioManager = context.getSystemService<AudioManager>()!!

    override fun doWork(): Result {

        //todo - this needs to be configable
        val targetVolumePercent = .2
        //todo - the duration also needs to be configurable
        val duration = 30.seconds
        Timber.d("duration millis = $duration")
        // maybe use shared prefs to store these values

        val maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC) // max is 15 on simulator
        val currentVolume = audioManager.musicVolume // should be 3 at the moment
        val targetVolume = (maxVolume * targetVolumePercent).toInt()
        Timber.d("targetVolume = $targetVolume")

        val diff = targetVolume - currentVolume // assuming 10

        val range = when {
            diff < 0 -> -1 downTo diff
            diff > 0 -> 1 until (diff + 1)
            // return from the function (not this when statement)
            else -> return Result.SUCCESS
        }

        // must be absolute incase we are decreasing the volume
        val timeBetweenIncrements = abs(duration.toFloat() / diff)
        Timber.d("timeBetweenIncrements = $timeBetweenIncrements")

        range.forEach { i ->
            audioManager.musicVolume = currentVolume + i
            Timber.d("currentVolume = ${audioManager.musicVolume}")
            Thread.sleep(timeBetweenIncrements.toLong())
        }


        Timber.d("""doWork:
            |maxVolume = $maxVolume,
            |targetVolume = $targetVolume
            |currentVolume = ${audioManager.musicVolume},
            |""".trimMargin())

        return Result.SUCCESS
    }
}