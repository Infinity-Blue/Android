package com.example.bookfinder_reviewer

import AndroidBug5497Workaround
import android.content.DialogInterface
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.bookfinder_reviewer.UI.Fragment1
import com.example.bookfinder_reviewer.UI.Fragment2
import com.example.bookfinder_reviewer.Util.AppUtilSetting
import com.example.bookfinder_reviewer.Util.pager_Adapter
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class MainActivity : AppCompatActivity() {
//    var arrays = arrayOf<String>()
//    var count:Int=0
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    private lateinit var customAlertDialogView: View
    private lateinit var materialAlertDialogBuilder:MaterialAlertDialogBuilder

    private fun pagerAdapter():pager_Adapter {
        val adapter=pager_Adapter(this)
        adapter.addFragment(Fragment1())
        adapter.addFragment(Fragment2())
        return adapter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            MaterialAlertDialogBuilder(this@MainActivity, R.style.AlertDialogTheme)
//                .setTitle("Please Grant Permissions to use the app")
//                .setMessage("Camera and Writing to external storage is required to capture/save book image for search")
//                .setPositiveButton("GOT IT", object: DialogInterface.OnClickListener {
//                    override fun onClick(dialogInterface:DialogInterface, i:Int) {
//                        AppUtilSetting(this@MainActivity).checkPermission()
//                    }
//                })
////            AppUtilSetting(this).checkPermission()
//        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            MaterialAlertDialogBuilder(this@MainActivity,R.style.ThemeOverlay_App_MaterialAlertDialog)
                .setTitle("Please grant permissions to use the app")
                .setMessage(R.string.dialog_message)
                .setPositiveButton("GOT IT", object: DialogInterface.OnClickListener {
                    override fun onClick(dialogInterface:DialogInterface, i:Int) {
                        AppUtilSetting(this@MainActivity).checkPermission()
                    }
                }).show()
//            AppUtilSetting(this).checkPermission()
//        }
        }

        //get Root view added to current activity using SetContentView
        val mRootView: ViewGroup =
            (findViewById<View>(android.R.id.content) as ViewGroup).getChildAt(0) as ViewGroup
        val call= AndroidBug5497Workaround.assistActivity(this,R.id.viewInsideScroll)
        mRootView.getViewTreeObserver()
            .addOnGlobalLayoutListener(OnGlobalLayoutListener{call})

        tabLayout = findViewById(R.id.tabLayout) as TabLayout
        viewPager = findViewById(R.id.viewPager) as ViewPager2
//        var fragmentList = listOf(Fragment1(), Fragment2())
        viewPager.adapter =pagerAdapter()

        val tabLayoutTextArray = arrayOf("Manual search","Cover photo")

        TabLayoutMediator(tabLayout, viewPager,
            object : TabLayoutMediator.TabConfigurationStrategy {
                override fun onConfigureTab(@NonNull tab: TabLayout.Tab, position: Int) {
                    tab.text=tabLayoutTextArray[position]
//                  tab.setText("Tab " + (position + 1))
                }
            }).attach()

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                Log.d("onTabSelected? ", "Yes")
                val pos: Int=tab.position
                Toast.makeText(this@MainActivity, pos.toString(), Toast.LENGTH_SHORT).show()
                viewPager.getCurrentItem()
//                viewPager.setCurrentItem(tab.getPosition())
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }


    private fun launchCustomAlertDialog() {
        val camTitle=customAlertDialogView.findViewById(R.id.title_camera)as TextView
        materialAlertDialogBuilder.setView(customAlertDialogView)
//        .setTitle("Please grant Permissions to use the app")
//            .setMessage("Camera and Writing to external storage is required to capture/save book image for search")
            .setPositiveButton("Ok", object: DialogInterface.OnClickListener {
                override fun onClick(dialogInterface:DialogInterface, i:Int) {
                    AppUtilSetting(this@MainActivity).checkPermission()
                }
            }).show()

    }
}
