package com.test.camera2

import android.content.Context
import android.hardware.camera2.CameraManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions
import pub.devrel.easypermissions.PermissionRequest


class MainActivity : AppCompatActivity() {

    companion object {
        private const val REQUEST_CODE_CAMERA = 121
        private val PERMISSIONS_REQUIRED = arrayOf(android.Manifest.permission.CAMERA)
    }

    private val cameraManager: CameraManager? by lazy {
        getSystemService(Context.CAMERA_SERVICE) as? CameraManager
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (!EasyPermissions.hasPermissions(this, *PERMISSIONS_REQUIRED)) {
            EasyPermissions.requestPermissions(
                PermissionRequest.Builder(this, REQUEST_CODE_CAMERA, *PERMISSIONS_REQUIRED).build()
            )
        } else {
            displayCameraCount()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    @AfterPermissionGranted(REQUEST_CODE_CAMERA)
    fun onCameraPermissionGranted() {
        displayCameraCount()
    }

    private fun displayCameraCount() {
        Toast.makeText(
            this,
            "Number of Cameras: ${cameraManager?.cameraIdList?.size}",
            Toast.LENGTH_SHORT
        ).show()
    }
}
