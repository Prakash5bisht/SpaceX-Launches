package com.example.spacexlaunchtracker.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.spacexlaunchtracker.Constants
import com.example.spacexlaunchtracker.R
import com.example.spacexlaunchtracker.adapter.NavigationViewPagerAdapter
import com.example.spacexlaunchtracker.databinding.ActivityMainBinding
import com.example.spacexlaunchtracker.fragment.HomeFragment
import com.example.spacexlaunchtracker.fragment.SpaceXStoreFragment
import com.example.spacexlaunchtracker.viewmodel.LaunchesSharedViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewPagerAdapter: NavigationViewPagerAdapter
    private val launchesSharedViewModel: LaunchesSharedViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpViewPager()
        registerOnPageChangeCallback()
        setBottomNavigationBar()
        window.navigationBarColor = ContextCompat.getColor(this, R.color.black3)
    }

    private fun setUpViewPager(){
        viewPagerAdapter = NavigationViewPagerAdapter(supportFragmentManager,lifecycle)
        binding.viewPager.adapter = viewPagerAdapter
    }

    private fun registerOnPageChangeCallback(){
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                when(position){
                    Constants.Screens.HOME_SCREEN-> setSelectedScreen(R.id.menu_home)
                    Constants.Screens.STORE_SCREEN-> setSelectedScreen(R.id.menu_store)
                    Constants.Screens.SEARCH_SCREEN-> setSelectedScreen(R.id.menu_search)
                }
            }
        })
    }

    private fun setBottomNavigationBar(){
        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.menu_home->{
                    binding.viewPager.currentItem = Constants.Screens.HOME_SCREEN
                    return@setOnItemSelectedListener true
                }
                R.id.menu_store->{
                    binding.viewPager.currentItem = Constants.Screens.STORE_SCREEN
                    return@setOnItemSelectedListener true
                }
                R.id.menu_search->{
                    binding.viewPager.currentItem = Constants.Screens.SEARCH_SCREEN
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
        setSelectedScreen(R.id.menu_home)
    }

    private fun setSelectedScreen(id: Int){
        binding.bottomNavigationView.selectedItemId = id
    }
}