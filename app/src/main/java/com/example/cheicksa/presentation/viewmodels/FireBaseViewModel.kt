package com.example.cheicksa.presentation.viewmodels

import android.content.ContentValues.TAG
import android.content.Intent
import android.content.IntentSender
import android.provider.Settings.System.getString
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.cheicksa.R
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class FireBaseViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val signInRequest: BeginSignInRequest,
    private val oneTapClient: SignInClient
): ViewModel() {
    val signedIn = mutableStateOf(false)
    val inProgress = mutableStateOf(false)
    val popupNotification: MutableState<String?> = mutableStateOf(null)

    init {
        // Check if the user is already signed in (persistent state)
        checkUserLoggedIn()
        //logout() //TODO: remove this line
    }

    private fun checkUserLoggedIn() {
        if (auth.currentUser != null) {
            signedIn.value = true
        }
    }
    fun onSignUp(email: String, password: String, phone: String) {
        inProgress.value = true
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    task.result?.user?.sendEmailVerification()
                    if (task.result.user?.isEmailVerified == true) {
                        signedIn.value = true
                    }

                } else {
                    popupNotification.value = task.exception?.message +"exception"
                }
                inProgress.value = false
            }
    }

    fun login(email: String, password: String) {
        inProgress.value = true
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    signedIn.value = true
                } else {
                    popupNotification.value = task.exception?.message
                }
                inProgress.value = false
            }
    }

    fun resetPassword(email: String) {
        inProgress.value = true
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    popupNotification.value = "Check your email"
                } else {
                    popupNotification.value = task.exception?.message
                }
                inProgress.value = false
            }
    }

    fun updatePassword(password: String) {
        inProgress.value = true
        auth.currentUser?.updatePassword(password)
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    popupNotification.value = "Password updated"
                } else {
                    popupNotification.value = task.exception?.message
                }
                inProgress.value = false
            }
    }

    suspend fun signInWithIntent(intent: Intent?) {
        inProgress.value = true
        val credential = oneTapClient.getSignInCredentialFromIntent(intent)
        val token = credential.googleIdToken
        val googleCredentials = GoogleAuthProvider.getCredential(token, null)
        val userName = credential.id
        val password = credential.password
        auth.signInWithCredential(googleCredentials)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    signedIn.value = true
                } else {
                    popupNotification.value = task.exception?.message
                }
                inProgress.value = false
            }
    }

    suspend fun sendAuthRequest(): IntentSender? {
        return try {
            val result = oneTapClient.beginSignIn(signInRequest)
                .await()
            result.pendingIntent.intentSender
        }catch (e: ApiException) {
            Log.e(TAG, "No saved credentials", e)
            null
        }

    }


    fun logout() {
        auth.signOut()
        signedIn.value = false
    }
}
