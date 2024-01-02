package com.example.cheicksa.presentation.viewmodels

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.firebase.messaging.ktx.messaging
import kotlinx.coroutines.tasks.await




class MessagingService: FirebaseMessagingService() {

    private val notificationViewModel: NotificationViewModel = NotificationViewModel()

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("MessagingService", "onNewToken: $token")
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onMessageReceived(message: RemoteMessage) {
        message.notification?.let {
            val title = it.title
            val body = it.body
            val notificationViewModel: NotificationViewModel = NotificationViewModel()
            if (title != null && body != null) {
                notificationViewModel.sendNotification(title, body, this)
            }

            Log.d("MessagingService", "onMessageReceived: ${it.body}")
        }
    }

    override fun onMessageSent(msgId: String) {
        super.onMessageSent(msgId)
    }



}