package com.example.spacexlaunchtracker.api

import com.example.spacexlaunchtracker.model.SpaceXLaunchesResponse
import retrofit2.http.GET

interface SpaceXApi {
    @GET("v3/launches")
    suspend fun getSpaceXLaunches() : List<SpaceXLaunchesResponse>
}