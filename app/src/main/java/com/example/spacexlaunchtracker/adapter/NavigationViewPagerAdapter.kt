package com.example.spacexlaunchtracker.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.spacexlaunchtracker.Constants
import com.example.spacexlaunchtracker.fragment.HomeFragment
import com.example.spacexlaunchtracker.fragment.SearchFragment
import com.example.spacexlaunchtracker.fragment.SpaceXStoreFragment

class NavigationViewPagerAdapter(fragmentManager : FragmentManager, lifecycle : Lifecycle) : FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            Constants.Screens.HOME_SCREEN -> HomeFragment.newInstance()
            Constants.Screens.STORE_SCREEN -> SpaceXStoreFragment.newInstance()
            Constants.Screens.SEARCH_SCREEN -> SearchFragment.newInstance()
            else->{HomeFragment.newInstance()}
        }
    }

    override fun getItemCount(): Int = 3
}