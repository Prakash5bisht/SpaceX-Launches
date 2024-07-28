package com.example.spacexlaunchtracker.repository

import android.util.Log
import com.example.spacexlaunchtracker.api.SpaceXApi
import com.example.spacexlaunchtracker.model.SpaceXLaunchesResponse
import com.example.spacexlaunchtracker.uiutli.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SpaceXLaunchesRepository @Inject constructor(private val spaceXApi: SpaceXApi) {
    suspend fun getLaunches() : Flow<DataState<List<SpaceXLaunchesResponse>>> = flow {
        try {
            emit(DataState.Loading)
            val apiResponse = spaceXApi.getSpaceXLaunches()
            Log.d("wtf", "$apiResponse")
            if(apiResponse.isNotEmpty()){
                emit(DataState.Success(apiResponse))
            }
        }catch (e:Exception){
            emit(DataState.Error(e.message?:"Something Went Wrong"))
        }
    }
}