package com.example.rocketreserver

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.apollographql.apollo.coroutines.await
import com.example.rocketreserver.databinding.LaunchListFragmentBinding

class LaunchListFragment : Fragment() {
    private lateinit var binding: LaunchListFragmentBinding
    val apollo = Apollo()
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
        lifecycleScope.launchWhenResumed {
            apollo.queryLaunches()?.let { setupAdapter(it) }
        }
    }

    private fun setupAdapter(list: List<LaunchListQuery.Launch>) {
        binding.launches.layoutManager = LinearLayoutManager(requireContext())
        binding.launches.adapter = LaunchListAdapter(list)
    }
}