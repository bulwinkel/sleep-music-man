package com.bulwinkel.slemusman

import android.content.Context
import android.media.AudioManager
import androidx.core.content.getSystemService
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import timber.log.Timber

private var AudioManager.musicVolume: Int
    get() = getStreamVolume(AudioManager.STREAM_MUSIC)
    set(value) = setStreamVolume(AudioManager.STREAM_MUSIC, value, 0)

class ChangeVolumeWorker(
    private val context : Context,
    private val params : WorkerParameters
) : Worker(context, params) {

    private val audioManager = context.getSystemService<AudioManager>()!!

    override fun doWork(): Result {

        //todo - this needs to be configable
        val targetVolumePercent = .8
        //todo - the duration also needs to be configurable
        // maybe use shared prefs to store these values

        val maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC) // max is 15 on simulator
        val currentVolume = audioManager.musicVolume // should be 3 at the moment
        val targetVolume = (maxVolume * targetVolumePercent).toInt()
        Timber.d("targetVolume = $targetVolume")
        val diff = targetVolume - currentVolume // assuming 10
        val duration = 30 * 1000 //seconds
        val timeBetweenIncrements = duration / diff
        Timber.d("timeBetweenIncrements = $timeBetweenIncrements")

        for (i in 0..diff) {
            audioManager.musicVolume = currentVolume + i
            Timber.d("currentVolume = ${audioManager.musicVolume}")
            Thread.sleep(timeBetweenIncrements.toLong())
        }


        Timber.d("""doWork:
            |maxVolume = $maxVolume,
            |currentVolume = ${audioManager.musicVolume}
            |""".trimMargin())

        return Result.SUCCESS
    }
}