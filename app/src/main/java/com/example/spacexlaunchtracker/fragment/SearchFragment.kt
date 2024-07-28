package com.example.spacexlaunchtracker.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import com.example.spacexlaunchtracker.Constants
import com.example.spacexlaunchtracker.activity.LaunchDetailActivity
import com.example.spacexlaunchtracker.adapter.SpaceXLaunchesAdapter
import com.example.spacexlaunchtracker.base.BaseFragment
import com.example.spacexlaunchtracker.databinding.FragmentSearchBinding
import com.example.spacexlaunchtracker.extentions.isActive
import com.example.spacexlaunchtracker.intent.SpaceXLaunchesIntent
import com.example.spacexlaunchtracker.model.SpaceXLaunchesResponse
import com.example.spacexlaunchtracker.uiutli.DataState
import com.example.spacexlaunchtracker.viewmodel.LaunchesSharedViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : BaseFragment() {
    private lateinit var searchBinding: FragmentSearchBinding
    private val launchesSharedViewModel: LaunchesSharedViewModel by activityViewModels()
    private lateinit var launchesAdapter: SpaceXLaunchesAdapter
    private var originalList = ArrayList<SpaceXLaunchesResponse?>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        searchBinding = FragmentSearchBinding.inflate(layoutInflater)
        return searchBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpObservers()
    }

    private fun setSearchListener() {
        searchBinding.searchView.doOnTextChanged { text, _, _, _ ->
            val searchQuery = text.toString()
            if (searchQuery.isNotEmpty()) {
                val searchResult = originalList.filter { propItem ->
                    propItem?.let {
                        (
                                (it.missionName != null && it.missionName.contains(
                                    searchQuery,
                                    true
                                )) ||
                                        (it.launchYear != null && it.launchYear.contains(
                                            searchQuery,
                                            true
                                        )) ||
                                        (it.rocketInfo?.rocketName != null && it.rocketInfo.rocketName.contains(
                                            searchQuery,
                                            true
                                        ))
                                )
                    } ?: false
                }
                launchesAdapter.setData(searchResult)
                if (searchResult.isEmpty()) {
                    toggleRv(false)
                    toggleNoDataView(true)
                } else {
                    toggleRv(true)
                    toggleNoDataView(false)
                }
            } else {
                toggleRv(false)
                toggleNoDataView(false)
                launchesAdapter.setData(originalList)
            }
        }
    }

    private fun initAdapter(data: List<SpaceXLaunchesResponse>) {
        if (data.isNotEmpty()) {
            launchesAdapter = SpaceXLaunchesAdapter()
            launchesAdapter.setData(data)
            searchBinding.launchesRv.adapter = launchesAdapter
            launchesAdapter.onItemClick = {
                openLaunchDetailActivity(it)
            }
            setSearchListener()
        }
    }

    private fun openLaunchDetailActivity(launchDetail: SpaceXLaunchesResponse) {
        if (isActive()) {
            val intent = Intent(requireContext(), LaunchDetailActivity::class.java)
            intent.putExtra(Constants.SPACE_X_LAUNCH_DETAIL, launchDetail)
            startActivity(intent)
        }
    }

    private fun setUpObservers() {
        setLaunchListObserver()
        setLaunchResponseObserver()
    }

    private fun toggleRv(show: Boolean) {
        searchBinding.launchesRv.isVisible = show
    }

    private fun toggleNoDataView(show: Boolean) {
        searchBinding.tvNoDataFound.isVisible = show
    }

    private fun toggleProgressBar(show: Boolean) {
        searchBinding.circularProgress.isVisible = show
    }

    private fun setLaunchResponseObserver() {
        launchesSharedViewModel.spaceXLaunchesResponse.observe(viewLifecycleOwner) {
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
    }

    private fun setLaunchListObserver() {
        launchesSharedViewModel.launchList.observe(viewLifecycleOwner) {
            if (!it.isNullOrEmpty()) {
                originalList.addAll(it)
                initAdapter(it)
            } else {
                callLaunchListApi()
            }
        }
    }

    private fun callLaunchListApi() {
        launchesSharedViewModel.setStateEvent(SpaceXLaunchesIntent.GetSpaceXLaunches)
    }

    companion object {
        fun newInstance() = SearchFragment()
    }
}