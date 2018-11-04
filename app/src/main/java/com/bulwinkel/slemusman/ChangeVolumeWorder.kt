package com.bulwinkel.slemusman

import android.content.Context
import android.media.AudioManager
import androidx.core.content.getSystemService
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import timber.log.Timber


class ChangeVolumeWorder(
    private val context : Context,
    private val params : WorkerParameters
) : Worker(context, params) {

    override fun doWork(): Result {

        val audioManager = context.getSystemService<AudioManager>()!!
        val maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
        val currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
        Timber.d("doWork: maxVolume = $maxVolume, currentVolume = $currentVolume")

        return Result.SUCCESS
    }
}