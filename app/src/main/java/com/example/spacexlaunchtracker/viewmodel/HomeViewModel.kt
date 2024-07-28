package com.example.spacexlaunchtracker.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.spacexlaunchtracker.base.BaseViewModel
import com.example.spacexlaunchtracker.intent.SpaceXLaunchesIntent
import com.example.spacexlaunchtracker.model.SpaceXLaunchesResponse
import com.example.spacexlaunchtracker.repository.SpaceXLaunchesRepository
import com.example.spacexlaunchtracker.uiutli.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val spaceXLaunchesRepository: SpaceXLaunchesRepository): BaseViewModel<SpaceXLaunchesIntent>() {

    private var _spaceXLaunchesResponse: MutableLiveData<DataState<List<SpaceXLaunchesResponse>>> = MutableLiveData()
    val spaceXLaunchesResponse: LiveData<DataState<List<SpaceXLaunchesResponse>>>
        get() = _spaceXLaunchesResponse

    override fun setStateEvent(intent: SpaceXLaunchesIntent) {
        when(intent){
            is SpaceXLaunchesIntent.GetSpaceXLaunches->{
                viewModelScope.launch {
                    spaceXLaunchesRepository.getLaunches().onEach {
                        _spaceXLaunchesResponse.value = it
                    }.collect()
                }
            }
        }
    }
}