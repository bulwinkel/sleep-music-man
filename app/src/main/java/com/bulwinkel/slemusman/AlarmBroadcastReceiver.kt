package com.bulwinkel.slemusman

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import timber.log.Timber

class AlarmBroadcastReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        Timber.i("onReceive: intent = $intent")
        val changeVolumeWork = OneTimeWorkRequestBuilder<ChangeVolumeWorder>()
            .build()
        WorkManager.getInstance().enqueue(changeVolumeWork)
    }
}