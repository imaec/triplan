package com.imaec.triplan.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.imaec.triplan.ui.main.home.HomeFragment
import com.imaec.triplan.ui.main.myplace.MyPlaceFragment
import com.imaec.triplan.ui.main.search.SearchFragment
import com.imaec.triplan.ui.main.setting.SettingFragment

class MainPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount() = 4

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> HomeFragment()
            1 -> SearchFragment()
            2 -> MyPlaceFragment()
            else -> SettingFragment()
        }
    }
}
