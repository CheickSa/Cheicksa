package com.example.cheicksa.presentation.permission

import android.Manifest
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cheicksa.ui.theme.CheicksaTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState
import javax.annotation.meta.When

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
@Composable
fun Permission(
    permission: String = Manifest.permission.POST_NOTIFICATIONS,
    action: () -> Unit = {},
    permissionTitle: String = "Permission",
    text: String = "You need to accept the permission to use this feature",
    textSetting: String = "You need to accept the permission to use this feature",

) {
    val permissionState = rememberPermissionState(
        permission = permission,
    )
    val context = LocalContext.current

    when (permissionState.status) {
        is PermissionStatus.Denied -> {
            if ((permissionState.status as PermissionStatus.Denied).shouldShowRationale) {
                var dismiss by remember{ mutableStateOf(true) }
                if (dismiss) {
                    ModalBottomSheet(onDismissRequest = { dismiss = false }) {
                        ShowPermission(
                            title = permissionTitle,
                            text = textSetting,
                            onClickAccept = {
                                openAppSettings(context)
                            },
                            onClickRefuse = {
                                dismiss = false
                            },
                            acceptText = "Ouvrir les paramètres",

                        )
                    }
                }
            }else{
                var dismiss by remember{ mutableStateOf(true) }
                if (dismiss) {
                    ModalBottomSheet(onDismissRequest = { dismiss = false }) {
                        ShowPermission(
                            title = permissionTitle,
                            text=text,
                            onClickAccept = {
                                permissionState.launchPermissionRequest()
                            },
                            onClickRefuse = {
                                dismiss = false
                            }
                        )
                    }
                }
            }
        }
        PermissionStatus.Granted -> {
            action()
        }
    }

}

@Composable
fun ShowPermission(
    title: String = "Permission",
    text: String = "You need to accept the permission to use this feature",
    onClickAccept : () -> Unit = { /*TODO*/ },
    onClickRefuse : () -> Unit = { /*TODO*/ },
    acceptText: String = "Accept",
    refuseText: String = "Refuser"
) {
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp, bottom = 30.dp)
    ){
        Text(
            text = title,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp),
            fontSize = 24.sp,
            fontWeight = FontWeight.ExtraBold,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.padding(10.dp))
        Text(
            text = text,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp),
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f)
        )
        Spacer(modifier = Modifier.padding(30.dp))
        Button(onClick = onClickAccept,modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .padding(start = 10.dp, end = 10.dp),
            shape = RoundedCornerShape(16.dp)

        ) {
            Text(text = acceptText)
        }
        Spacer(modifier = Modifier.padding(8.dp))
        OutlinedButton(
            onClick = onClickRefuse,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(start = 10.dp, end = 10.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(text = refuseText)
        }
    }

}

@Preview
@Composable
private fun _ShowPermission() {
    CheicksaTheme {
        ShowPermission()
    }
}

fun openAppSettings(context: Context) {
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
    val uri = Uri.fromParts("package", "com.example.cheicksa", null)
    intent.data = uri
    context.startActivity(intent)
}