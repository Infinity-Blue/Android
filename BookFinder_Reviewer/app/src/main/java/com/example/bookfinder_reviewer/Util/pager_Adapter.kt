package com.example.bookfinder_reviewer.Util

//import androidx.fragment.app.FragmentStatePagerAdapter
import android.util.Log
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class pager_Adapter(@NonNull fragmentActivity: AppCompatActivity):FragmentStateAdapter(fragmentActivity) {
    var fragmentList = arrayListOf<Fragment>()

    override fun getItemCount(): Int {
//        return ITEM_SIZE
        Log.d("fragment size? :", fragmentList.size.toString())
        return fragmentList.size
    }

    fun addFragment(fragment: Fragment) {
        Log.d("fragment added? :","yes")
        fragmentList.add(fragment)
    }

    @NonNull
    override fun createFragment(position:Int): Fragment {
        Log.d("CreateFragment ?", "yes")
//        var fragmentPage: Fragment? =null
//        Log.d("Fragment position", position.toString())
//        when(position){
//            0-> fragmentPage=Fragment1()
//            1-> fragmentPage=Fragment2()
//        }
//        return fragmentPage!!
        return fragmentList.get(position)
    }
//    companion object {
//        private val ITEM_SIZE = 2
//    }
//    abstract fun implement():pagerAdapter
}