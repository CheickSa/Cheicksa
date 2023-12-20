package com.example.cheicksa.presentation.viewmodels

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.graphics.drawable.Icon
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.mutableStateOf
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.ViewModel
import com.example.cheicksa.MainActivity
import com.example.cheicksa.R

val CHANNEL_ID = "Main Channel ID"

enum class NotificationAction {
    CREATE,
    UPDATE,
    CANCEL

}
class NotificationViewModel : ViewModel() {

    private val askNotification = mutableStateOf(false)


    /**
     * Sends a notification with the specified parameters.
     *
     * @param title The title of the notification (default is "Order").
     * @param message The content text of the notification (default is "Your order is ready").
     * @param context The context in which the notification is sent.
     * @param action The action to perform with the notification (CREATE, UPDATE, or CANCEL).
     * @param uniqueId The unique identifier for the notification.
     */
    @RequiresApi(Build.VERSION_CODES.O)
    fun sendNotification (
        title: String = "Order",
        message: String = "Your order is ready",
        context: Context,
        action: NotificationAction = NotificationAction.CREATE,
        uniqueId: Int = 1,
        icon: Icon? = null
    ){
        val notificationManager = getSystemService(context,android.app.NotificationManager::class.java) as NotificationManager
        val channel = NotificationChannel(
            CHANNEL_ID,
            "Main Channel",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        notificationManager.createNotificationChannel(channel)

        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.money)
            .setLargeIcon(icon)

            .setContentTitle(title)
            .setContentText(message)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(context)) {
            // notificationId is a unique int for each notification that you must define.
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED
            ) { askNotification.value = true;return }

            when(action){
                NotificationAction.CREATE -> notify(uniqueId, builder.build())
                NotificationAction.UPDATE -> notify(uniqueId, builder.setContentText(message).build())
                NotificationAction.CANCEL -> cancel(uniqueId)
            }


        }
    }

}