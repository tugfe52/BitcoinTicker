package com.development.bitcointicker.utils.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import com.development.bitcointicker.R
import com.development.bitcointicker.utils.constants.AppConstants

object NotificationImp {
    fun showNotification(context: Context, content: String, title: String) {

        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                AppConstants.NOTIFICATION_ID,
                AppConstants.NOTIFICATION_CHANNEL,
                NotificationManager.IMPORTANCE_HIGH
            )
            manager.createNotificationChannel(channel)
        }

        val builder = NotificationCompat.Builder(context, AppConstants.NOTIFICATION_ID).setContentText(content).setContentTitle(title).setSmallIcon(
            R.drawable.app_logo).setPriority(NotificationCompat.PRIORITY_HIGH)

        manager.notify(1, builder.build())
    }

}