package com.example.spacexlaunchtracker.intent

sealed class SpaceXLaunchesIntent {
    object GetSpaceXLaunches: SpaceXLaunchesIntent()
}