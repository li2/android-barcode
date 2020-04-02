package me.li2.android.barcode

import android.content.Intent
import androidx.fragment.app.Fragment
import com.google.zxing.integration.android.IntentIntegrator

/**
 * zxing onActivityResult not called in Fragment only in Activity
 * https://stackoverflow.com/a/22320076/2722270
 */
internal class FragmentIntentIntegrator(private val fragment: Fragment)
    : IntentIntegrator(fragment.activity) {

    override fun startActivityForResult(intent: Intent?, code: Int) {
        fragment.startActivityForResult(intent, code)
    }
}
