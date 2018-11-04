package com.bulwinkel.slemusman

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.AlarmManagerCompat
import androidx.core.content.getSystemService
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val alarmManager = getSystemService<AlarmManager>()!!

        val showActivity = Intent(this, this::class.java)
        val showActivityPending = PendingIntent.getActivity(
            this,
            1,
            showActivity,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val startWorkIntent = Intent(this, AlarmBroadcastReceiver::class.java)

        val startWorkPending = PendingIntent.getBroadcast(
            this,
            2,
            startWorkIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val alarmTime = System.currentTimeMillis() + (5 * 1000)
        AlarmManagerCompat.setAlarmClock(
            alarmManager,
            alarmTime,
            showActivityPending,
            startWorkPending
        )
    }
}
