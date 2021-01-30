package com.example.rocketreserver.Presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.example.rocketreserver.R
import com.example.rocketreserver.databinding.LaunchItemBinding
import queries.LaunchListQuery

class LaunchListAdapter(
    val launches : List<LaunchListQuery.Launch>
) : RecyclerView.Adapter<LaunchListAdapter.ViewHolder>() {


    class ViewHolder(val binding: LaunchItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun getItemCount(): Int {
        return launches.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = LaunchItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    var onEndOfListReached: (() -> Unit)? = null
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val launch = launches[position]
        with(holder.binding){
            site.text = launch.site ?: "Launch site unknown"
            missionName.text = launch.mission?.name
            missionPatch.load(launch.mission?.missionPatch){
                placeholder(R.drawable.ic_placeholder)
            }
        }
        if (position == launches.size - 1) {
            onEndOfListReached?.invoke()
        }

    }
}