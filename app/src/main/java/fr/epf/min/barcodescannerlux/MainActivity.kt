package fr.epf.min.barcodescannerlux

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.budiyev.android.codescanner.*
import fr.epf.min.barcodescannerlux.API.ProductApi
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.*
import java.io.IOException

private const val CAMERA_REQUEST_CODE = 101
class MainActivity : AppCompatActivity() {
    var barcode = ""
    private lateinit var codeScanner: CodeScanner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button_scan.setOnClickListener {
            if (barcode != "") {
                val intent = Intent(this, ProductActivity::class.java)
                intent.putExtra("code", barcode)
                startActivity(intent)
            } else {
                val toast = Toast.makeText(
                    applicationContext,
                    "Vous devez d'abord scanner un code bar.",
                    Toast.LENGTH_SHORT
                )
                toast.show()
            }
        }

            setupPermissions()
            codeScanner()
        }

        private fun codeScanner() {
            val scanner_view = findViewById<CodeScannerView>(R.id.scanner_view)
            val scanner_text = findViewById<TextView>(R.id.scanner_text)
            codeScanner = CodeScanner(this, scanner_view)
            codeScanner.apply {
                camera = CodeScanner.CAMERA_BACK
                formats = CodeScanner.ALL_FORMATS

                autoFocusMode = AutoFocusMode.SAFE
                scanMode = ScanMode.CONTINUOUS
                isAutoFocusEnabled = true
                isFlashEnabled = false

                decodeCallback = DecodeCallback {
                    runOnUiThread {
                        scanner_text.text = it.text
                        barcode = it.toString()
                    }
                }

                errorCallback = ErrorCallback {
                    runOnUiThread {
                        Log.e("Main", "Camera initialization error: ${it.message}")
                    }
                }

                scanner_view.setOnClickListener {
                    codeScanner.startPreview()
                }
            }
        }

        override fun onResume() {
            super.onResume()
            codeScanner.startPreview()
        }

        override fun onPause() {
            codeScanner.releaseResources()
            super.onPause()
        }

        private fun setupPermissions() {
            val permission =
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)

            if (permission != PackageManager.PERMISSION_GRANTED) {
                makeRequest()
            }
        }

        private fun makeRequest() {
            ActivityCompat.requestPermissions(
                this, arrayOf(android.Manifest.permission.CAMERA),
                CAMERA_REQUEST_CODE
            )
        }

        override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
        ) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            when (requestCode) {
                CAMERA_REQUEST_CODE -> {
                    if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(
                            this,
                            "Vous devez autoriser l'utilisation de la caméra pour utiliser l'application",
                            Toast.LENGTH_SHORT
                        )
                    } else {
                        //accès autorisé
                    }
                }
            }
        }

    }


