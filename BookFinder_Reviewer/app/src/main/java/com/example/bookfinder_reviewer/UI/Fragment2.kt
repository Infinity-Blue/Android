package com.example.bookfinder_reviewer.UI

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle;
import android.provider.Settings
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton
import android.widget.ImageView
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import com.example.bookfinder_reviewer.R
import com.example.bookfinder_reviewer.Util.AppUtilSetting

class Fragment2 : Fragment() {
    @Nullable
    override fun onCreateView(
        inflater: LayoutInflater,
        @Nullable container: ViewGroup?,
        @Nullable savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment2, container,false)
    }


override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    val camButton: ImageView = view.findViewById(R.id.cameraButton)
//    camButton.setOnClickListener(object: View.OnClickListener {
//        override fun onClick(view:View) {
//            val internet = AppUtilSetting(requireContext(),requireActivity()).isNetworkConnectionAvailable()
//            if (internet== true)
//            {
//                val intent = Intent(getActivity(), CamImage::class.java)
//                startActivity(intent)
//            }
//            else if (internet== false)
//            {
//                //Showing AlertDialog if Internet Connection is off
//                val alertBuilder = AlertDialog.Builder(getActivity())
//                alertBuilder.setTitle("No Internet connection")
//                alertBuilder.setCancelable(false)
//                alertBuilder.setMessage("Please turn on your internet connection to continue.")
//                alertBuilder.setPositiveButton("Turn on", object: DialogInterface.OnClickListener {
//                    override fun onClick(dialogInterface:DialogInterface, i:Int) {
//                        startActivity(Intent(Settings.ACTION_WIRELESS_SETTINGS))
//                    }
//                })
//                alertBuilder.setNegativeButton("Cancel", object: DialogInterface.OnClickListener {
//                    override fun onClick(dialogInterface:DialogInterface, i:Int) {
//                        dialogInterface.cancel()
//                    }
//                })
//                val dialog = alertBuilder.create()
//                dialog.show()
//            }
//        }
//    })

}

}