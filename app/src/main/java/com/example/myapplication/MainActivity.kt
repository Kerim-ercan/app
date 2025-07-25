package com.example.yourappname

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

// Activity that shows camera preview and takes a photo
import com.example.yourappname.CameraActivity

class MainActivity : AppCompatActivity() {

    companion object {
        private const val CAMERA_PERMISSION_CODE = 100
        private const val CAMERA_REQUEST_CODE = 101
    }

    private lateinit var cameraButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Find the button by its ID
        cameraButton = findViewById(R.id.camera_button)

        // Set click listener for the button
        cameraButton.setOnClickListener {
            // Check if camera permission is granted
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                // Permission already granted, open camera
                openCamera()
            } else {
                // Request camera permission
                ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.CAMERA), CAMERA_PERMISSION_CODE)
            }
        }
    }

    private fun openCamera() {
        val intent = Intent(this, CameraActivity::class.java)
        startActivityForResult(intent, CAMERA_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {
            val path = data?.getStringExtra("photoPath")
            Toast.makeText(this, "Photo saved: $path", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, open camera
                openCamera()
            } else {
                // Permission denied
                Toast.makeText(this, "Camera permission is required to use camera",
                    Toast.LENGTH_SHORT).show()
            }
        }
    }
}