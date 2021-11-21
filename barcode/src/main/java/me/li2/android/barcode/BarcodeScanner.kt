@file:Suppress("unused")

package me.li2.android.barcode

import android.app.Activity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.google.zxing.integration.android.IntentIntegrator
import com.petarmarijanovic.rxactivityresult.RxActivityResult
import hu.akarnokd.rxjava3.bridge.RxJavaBridge
import io.reactivex.rxjava3.core.Observable

object BarcodeScanner {

    fun scan(fragment: Fragment): Observable<String> {
        if (fragment.activity == null || fragment.activity?.isFinishing.orFalse()) {
            throw RuntimeException("Activity is finishing")
        }
        return scan(requireNotNull(fragment.activity), FragmentIntentIntegrator(fragment))
    }

    fun scan(activity: FragmentActivity): Observable<String> {
        if (activity.isFinishing.orFalse()) {
            throw RuntimeException("Activity is finishing")
        }
        return scan(activity, IntentIntegrator(activity))
    }

    private fun scan(
        activity: FragmentActivity,
        intentIntegrator: IntentIntegrator
    ): Observable<String> {
        val scanIntent = intentIntegrator
            .setCaptureActivity(BarcodeScannerActivity::class.java)
            .createScanIntent()

        return RxActivityResult(activity)
            .start(scanIntent)
            .map { result ->
                val resultCode = result.resultCode
                if (resultCode == Activity.RESULT_OK) {
                    val scanResult = IntentIntegrator.parseActivityResult(resultCode, result.data)
                    if (scanResult.contents != null) {
                        return@map scanResult.contents
                    }
                }
                return@map ""
            }
            .toObservable()
            .`as`(RxJavaBridge.toV3Observable<String>())
    }
}

private fun Boolean?.orFalse() = this ?: false
