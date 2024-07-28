package com.example.spacexlaunchtracker.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.spacexlaunchtracker.Constants
import com.example.spacexlaunchtracker.R
import com.example.spacexlaunchtracker.activity.LaunchDetailActivity
import com.example.spacexlaunchtracker.adapter.SpaceXLaunchesAdapter
import com.example.spacexlaunchtracker.base.BaseFragment
import com.example.spacexlaunchtracker.databinding.FragmentHomeBinding
import com.example.spacexlaunchtracker.extentions.isActive
import com.example.spacexlaunchtracker.intent.SpaceXLaunchesIntent
import com.example.spacexlaunchtracker.model.SpaceXLaunchesResponse
import com.example.spacexlaunchtracker.uiutli.DataState
import com.example.spacexlaunchtracker.viewmodel.HomeViewModel
import com.example.spacexlaunchtracker.viewmodel.LaunchesSharedViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : BaseFragment() {
    private lateinit var homeBinding: FragmentHomeBinding
    private lateinit var launchesAdapter: SpaceXLaunchesAdapter
    private val homeViewModel: HomeViewModel by viewModels()
    private val launchesSharedViewModel: LaunchesSharedViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpObservers()
        setUpAppBar()
    }

    private fun setUpAppBar(){
        homeBinding.appBar.backBtn.isVisible = false
        homeBinding.appBar.title.text = getString(R.string.spacex_launches)
    }

    private fun initAdapter(data: List<SpaceXLaunchesResponse>) {
        if (data.isNotEmpty()) {
            launchesAdapter = SpaceXLaunchesAdapter()
            launchesAdapter.setData(data)
            launchesAdapter.onItemClick = {
                openLaunchDetailActivity(it)
            }
            homeBinding.launchesRv.adapter = launchesAdapter
        }
    }

    override fun onResume() {
        super.onResume()
        callLaunchListApi()
    }

    private fun openLaunchDetailActivity(launchDetail: SpaceXLaunchesResponse){
        if(isActive()){
            val intent = Intent(requireContext(), LaunchDetailActivity::class.java)
            intent.putExtra(Constants.SPACE_X_LAUNCH_DETAIL, launchDetail)
            startActivity(intent)
        }
    }

    private fun setUpObservers() {
        homeViewModel.spaceXLaunchesResponse.observe(viewLifecycleOwner) {
            when (it) {
                is DataState.Loading -> {
                    toggleProgressBar(true)
                }

                is DataState.Success -> {
                    toggleProgressBar(false)
                    toggleRv(true)
                    initAdapter(it.data)
                    launchesSharedViewModel.setResponse(it.data)
                }

                is DataState.Error -> {
                    toggleProgressBar(false)
                    toggleRv(false)
                    if (isActive()) {
                        showToast(requireContext(), it.error)
                    }
                }
            }
        }
        callLaunchListApi()
    }

    private fun callLaunchListApi(){
        homeViewModel.setStateEvent(SpaceXLaunchesIntent.GetSpaceXLaunches)
    }

    private fun toggleRv(show: Boolean) {
        homeBinding.launchesRv.isVisible = show
    }

    private fun toggleProgressBar(show: Boolean) {
        homeBinding.circularProgress.isVisible = show
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeBinding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return homeBinding.root
    }

    companion object {
        fun newInstance() = HomeFragment()
    }
}