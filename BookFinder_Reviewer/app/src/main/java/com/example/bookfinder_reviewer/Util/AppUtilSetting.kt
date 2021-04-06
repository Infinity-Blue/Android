package com.example.bookfinder_reviewer.Util

import android.Manifest
import android.R
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.ContextCompat
import com.google.android.material.internal.ContextUtils.getActivity
import com.google.android.material.snackbar.Snackbar


class AppUtilSetting(val context: Context): AppCompatActivity() {
    companion object {
//        const val MY_PERMISSIONS_REQUEST_CAMERA = 20
        const val MULTIPLE_PERMISSIONS_REQUEST = 20
    }
    val activity=context as Activity

    fun isNetworkConnectionAvailable(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
       //One's device's API level is 29 or higher
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val nw= connectivityManager.activeNetwork ?: return false
            val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
            return when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                //for other device how are able to connect with Ethernet
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                //for check internet over Bluetooth
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
                else -> false
            }
        } else {
            val nwInfo = connectivityManager.activeNetworkInfo ?: return false
            return nwInfo.isConnected
        }
    }


    fun checkPermission() {
        if (ContextCompat.checkSelfPermission(
                activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) + ContextCompat.checkSelfPermission(activity,
                    Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED) {
            //Ask the user for permission
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) || ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.CAMERA)) {
                Snackbar.make(activity.findViewById(R.id.content), "Please Grant Permissions to search book",
                    Snackbar.LENGTH_INDEFINITE).setAction("ENABLE", object : View.OnClickListener {
                        override fun onClick(v: View?) {
                            //"Dont ask me again + Deny" option included
                            requestPermissions(activity, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA),
                                MULTIPLE_PERMISSIONS_REQUEST) }
                    }).show()
            }
            else {
                Toast.makeText(activity,"requestPermissions",Toast.LENGTH_SHORT).show()
                //onRequestPermissionsResult is called
                requestPermissions(activity,arrayOf(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA
                    ), MULTIPLE_PERMISSIONS_REQUEST
                )
            }
        }
        //permission already granted
        else {
        }
    }


    /*
     * Callback received when a permissions request has been completed.
     */
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            MULTIPLE_PERMISSIONS_REQUEST -> if (grantResults.size > 0) {
                val cameraPermission =
                    grantResults[1] == PackageManager.PERMISSION_GRANTED
                val readExternalFile =
                    grantResults[0] == PackageManager.PERMISSION_GRANTED
                if (cameraPermission && readExternalFile) {
                }
                // permission denied
                else {
                    Snackbar.make(activity.findViewById(R.id.content), "Please Grant Permissions to search book", Snackbar.LENGTH_INDEFINITE
                    ).setAction(
                        "ENABLE"
                    ) {
                        requestPermissions(activity,arrayOf(
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.CAMERA), MULTIPLE_PERMISSIONS_REQUEST
                        )
                    }.show()
                }
            }
        }
    }
}