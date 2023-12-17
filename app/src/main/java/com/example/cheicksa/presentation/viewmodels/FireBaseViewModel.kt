package com.example.cheicksa.presentation.viewmodels

import android.app.Application
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.provider.Settings.System.getString
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import com.example.cheicksa.R
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.common.api.ApiException
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthMissingActivityForRecaptchaException
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import dagger.hilt.android.internal.Contexts.getApplication
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.tasks.await
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class FireBaseViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val signInRequest: BeginSignInRequest,
    private val oneTapClient: SignInClient
): ViewModel() {
    //val context = LocalContext
    val signedIn = mutableStateOf(false)
    val inProgress = mutableStateOf(false)
    val popupNotification: MutableState<String?> = mutableStateOf(null)
    private val _isPhoneVerified = mutableStateOf(false)
    val isPhoneVerified: State<Boolean> = _isPhoneVerified
    private var storedVerificationId: String? = null
    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken

    private var _emailSent = mutableStateOf(false)
    val emailSent: State<Boolean> = _emailSent

    private val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            Log.d(TAG, "onVerificationCompleted:$credential")
            signInWithPhoneAuthCredential(credential)
        }

        override fun onVerificationFailed(e: FirebaseException) {

            Log.w(TAG, "onVerificationFailed", e)

            if (e is FirebaseAuthInvalidCredentialsException) {
                // Invalid request
            } else if (e is FirebaseTooManyRequestsException) {
                // The SMS quota for the project has been exceeded
            } else if (e is FirebaseAuthMissingActivityForRecaptchaException) {
                // reCAPTCHA verification attempted with null Activity
            }
        }

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken,
        ) {
            Log.d(TAG, "onCodeSent:$verificationId")
            storedVerificationId = verificationId
            resendToken = token
        }
    }

    init {
        // Check if the user is already signed in (persistent state)
        checkUserLoggedIn()
        auth.setLanguageCode("fr")
        //auth.useAppLanguage()
        logout() //TODO: remove this line
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
                        ?.addOnCompleteListener {
                            if (task.isSuccessful) {
                                popupNotification.value = "Verifiez votre email"
                            } else {
                                popupNotification.value = task.exception?.message
                            }
                        }
                    if (auth.currentUser?.isEmailVerified==true) {
                        signedIn.value = true
                    }
                } else {
                    popupNotification.value = task.exception?.message
                }
                inProgress.value = false
            }


    }

    fun verifyPhone(phone: String){
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phone)
            .setTimeout(60L, TimeUnit.SECONDS)
            .requireSmsValidation(true)
            .setCallbacks(callbacks)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    fun login(email: String, password: String, ) {
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

    fun resetPassword(email: String,  ) {
        inProgress.value = true
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _emailSent.value = true
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
            popupNotification.value = e.message
            Log.e(TAG, "No saved credentials", e)
            null
        }

    }


    fun logout() {
        auth.signOut()
        signedIn.value = false
    }
    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _isPhoneVerified.value = true
                    val user = task.result?.user
                } else {
                    // Sign in failed, display a message and update the UI
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                    }
                    // Update UI
                }
            }
    }
}
