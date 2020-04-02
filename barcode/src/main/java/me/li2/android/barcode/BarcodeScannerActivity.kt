package me.li2.android.barcode

import android.app.Activity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.LifecycleOwner
import com.journeyapps.barcodescanner.CaptureManager
import com.journeyapps.barcodescanner.DecoratedBarcodeView

/**
 * Scanner Activity extending from Activity to display a custom layout form scanner view.
 *
 * zxing-android-embedded
 * https://github.com/journeyapps/zxing-android-embedded
 * https://github.com/journeyapps/zxing-android-embedded/blob/209e1c9a04725dd068a4e29c608315a3a7e9b378/sample/src/main/java/example/zxing/CustomScannerActivity.java
 */
internal class BarcodeScannerActivity : AppCompatActivity(), LifecycleOwner {

    private lateinit var capture: CaptureManager
    private lateinit var barcodeScannerView: DecoratedBarcodeView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        requestWindowFeature(Window.FEATURE_NO_TITLE)
//        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.barcode_scanner_activity)
//        initToolBar()
        initScannerView(savedInstanceState)

        findViewById<View>(R.id.back_button).setOnClickListener {
            finish()
        }
    }

    private fun initScannerView(savedInstanceState: Bundle?) {
        barcodeScannerView = findViewById(R.id.zxing_barcode_scanner)
        capture = CaptureManager(this, barcodeScannerView)
        capture.initializeFromIntent(intent, savedInstanceState)
        capture.decode()
    }

    private fun initToolBar() {
//        setToolbar(tb_scanner, getString(R.string.barcode_scanner_toolbar_title))
    }

    override fun onResume() {
        super.onResume()
        capture.onResume()
    }

    override fun onPause() {
        capture.onPause()
        super.onPause()
    }

    override fun onDestroy() {
        capture.onDestroy()
        super.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        capture.onSaveInstanceState(outState)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        return barcodeScannerView.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event)
    }
}

private fun Activity.setToolbar(toolbar: Toolbar,
                                title: String = "", @DrawableRes iconId: Int? = null) {
    (this as? AppCompatActivity)?.apply {
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setTitle(title)
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            setHomeButtonEnabled(true)
        }
        iconId?.run { toolbar.setNavigationIcon(this) }
    }
}