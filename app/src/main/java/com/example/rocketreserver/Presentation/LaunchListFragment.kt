package com.example.rocketreserver.Presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rocketreserver.Data.Apollo
import com.example.rocketreserver.databinding.LaunchListFragmentBinding
import kotlinx.coroutines.channels.Channel
import queries.LaunchListQuery

class LaunchListFragment : Fragment() {
    private lateinit var binding: LaunchListFragmentBinding
    private val apollo = Apollo()
    val channel = Channel<Unit>(Channel.CONFLATED)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = LaunchListFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val launches = mutableListOf<LaunchListQuery.Launch>()
        val adapter = setupAdapter(launches)
        lifecycleScope.launchWhenResumed {
            binding.loading.show()
            var cursor: String? = null
            for (item in channel) {
                val newLaunches = apollo.queryLaunches(cursor)
                newLaunches?.launches?.filterNotNull()?.let {
                    launches.addAll(it)
                    binding.launches.adapter?.notifyDataSetChanged()
                }
                cursor = newLaunches?.cursor
                if (newLaunches?.hasMore != true)
                    break
                if(adapter.itemCount > 0)
                    binding.loading.hide()
            }
            adapter.onEndOfListReached = { channel.offer(Unit) }

        }

    }

    private fun setupAdapter(list: List<LaunchListQuery.Launch>): LaunchListAdapter {
        channel.offer(Unit)
        binding.launches.layoutManager = LinearLayoutManager(requireContext())
        return LaunchListAdapter(list).apply {
            binding.launches.adapter = this
            onEndOfListReached = { channel.offer(Unit) }
        }
    }
}