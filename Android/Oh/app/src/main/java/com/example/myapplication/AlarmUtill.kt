package com.example.myapplication

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.SystemClock
import androidx.core.content.ContextCompat.getSystemService

class AlarmUtill(private val context: Context) {
    fun Alarm() {
        val AlarmIntent = Intent(context, AlarmReceiver::class.java)
        var firstTime = SystemClock.elapsedRealtime()
        firstTime += (10 * 1000).toLong()               // 이게 말이 10초지 30초정도 걸리더라(오차가 조금 있어요)
        val pendingIntent = PendingIntent.getBroadcast(context, 0, AlarmIntent, PendingIntent.FLAG_CANCEL_CURRENT)
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, firstTime, 5000, pendingIntent)
    }

    fun cancelAlarm() {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0)
        alarmManager.cancel(pendingIntent)

    }
}