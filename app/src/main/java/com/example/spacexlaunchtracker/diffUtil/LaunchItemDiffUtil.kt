package com.example.spacexlaunchtracker.diffUtil

import androidx.recyclerview.widget.DiffUtil
import com.example.spacexlaunchtracker.model.SpaceXLaunchesResponse

class LaunchItemDiffUtil(private val oldList : List<SpaceXLaunchesResponse?>?, private val newList : List<SpaceXLaunchesResponse?>?)
    : DiffUtil.Callback(){
    override fun getOldListSize(): Int {
        return oldList?.size ?: 0
    }

    override fun getNewListSize(): Int {
        return newList?.size ?: 0
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList?.get(oldItemPosition)?.flightNumber == newList?.get(newItemPosition)?.flightNumber
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList?.get(oldItemPosition) == newList?.get(newItemPosition)
    }
}