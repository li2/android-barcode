package me.li2.android.barcodesample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import me.li2.android.barcode.BarcodeScanner
import me.li2.android.view.popup.snackbar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        barcode_scan_button.setOnClickListener {
            scan()
        }
    }

    private fun scan() {
        doWithCameraPermission {
            BarcodeScanner.scan(this).subscribe { content ->
                if (content.isNotEmpty()) snackbar(content)
            }
        }
    }
}
