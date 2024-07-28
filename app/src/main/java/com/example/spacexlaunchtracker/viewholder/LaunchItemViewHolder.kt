package com.example.spacexlaunchtracker.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.example.spacexlaunchtracker.databinding.LayoutSpaceXLaunchItemBinding
import com.example.spacexlaunchtracker.model.SpaceXLaunchesResponse

class LaunchItemViewHolder(private val binding: LayoutSpaceXLaunchItemBinding): RecyclerView.ViewHolder(binding.root) {
    var onItemClick: ((launchDetail: SpaceXLaunchesResponse)->Unit)? = null
    fun bind(spaceXLaunchesResponse: SpaceXLaunchesResponse) {
        binding.tvMissionName.text = "\uD83C\uDFAF :- ${spaceXLaunchesResponse.missionName?:""}"
        binding.tvLaunchYear.text = "\uD83D\uDCC5 :- ${spaceXLaunchesResponse.launchYear?:""}"
        binding.tvRocketName.text = "\uD83D\uDE80 :- ${ spaceXLaunchesResponse.rocketInfo?.rocketName ?: "" }"
        binding.root.setOnClickListener {
            onItemClick?.invoke(spaceXLaunchesResponse)
        }
    }
}