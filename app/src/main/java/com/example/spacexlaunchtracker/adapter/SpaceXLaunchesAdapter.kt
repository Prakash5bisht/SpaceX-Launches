package com.example.spacexlaunchtracker.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.spacexlaunchtracker.databinding.LayoutSpaceXLaunchItemBinding
import com.example.spacexlaunchtracker.diffUtil.LaunchItemDiffUtil
import com.example.spacexlaunchtracker.model.SpaceXLaunchesResponse
import com.example.spacexlaunchtracker.viewholder.LaunchItemViewHolder

class SpaceXLaunchesAdapter(): RecyclerView.Adapter<LaunchItemViewHolder>() {
    private var launchesList : MutableList<SpaceXLaunchesResponse?> = arrayListOf()
    var onItemClick: ((launchDetail: SpaceXLaunchesResponse)->Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LaunchItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = LayoutSpaceXLaunchItemBinding.inflate(inflater, parent, false)
        return LaunchItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return launchesList.size
    }

    override fun onBindViewHolder(holder: LaunchItemViewHolder, position: Int) {
        val item = launchesList[position]
        item?.let {
            holder.bind(it)
            holder.onItemClick = {launchDetail->
                this.onItemClick?.invoke(launchDetail)
            }
        }
    }

    fun setData(newData: List<SpaceXLaunchesResponse?>?){
        val diffResult = DiffUtil.calculateDiff(LaunchItemDiffUtil(launchesList, newData))
        if (!newData.isNullOrEmpty()) {
            launchesList.clear()
            launchesList.addAll(newData)
            diffResult.dispatchUpdatesTo(this)
        }
    }
}