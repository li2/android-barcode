package me.li2.android.barcodesample

import android.content.Context
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import io.reactivex.rxjava3.disposables.Disposable
import me.li2.android.barcode.BarcodeScanner
import me.li2.android.common.framework.openAppSettings
import me.li2.android.common.rx.PermissionResult.*
import me.li2.android.common.rx.checkAndRequestCameraPermission
import me.li2.android.view.popup.snackbar

private fun cameraPermissionPrompt(context: Context): AlertDialog {
    return MaterialAlertDialogBuilder(context)
        .setTitle("\"Demo App\" Would Like to Access the Camera")
        .setMessage("This will let you read QR codes, take photos and record videos")
        .setPositiveButton("Yep!", null)
        .setNegativeButton("Nope!", null)
        .create()
}

private fun FragmentActivity.doWithCameraPermission(onGranted: () -> Unit): Disposable {
    return checkAndRequestCameraPermission(cameraPermissionPrompt(this))
        .subscribe { permissionResult ->
            when (permissionResult) {
                GRANTED -> onGranted()
                DENIED -> snackbar("The functionality is not available without camera permission!")
                DENIED_NOT_ASK_AGAIN -> openAppSettings(applicationContext.packageName)
            }
        }
}

fun scan(activity: FragmentActivity) {
    activity.doWithCameraPermission {
        BarcodeScanner.scan(activity).subscribe { content ->
            if (content.isNotEmpty()) activity.snackbar(content)
        }
    }
}
