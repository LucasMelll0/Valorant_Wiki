package com.example.valorantwiki.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.valorantwiki.databinding.FragmentMapsBinding
import com.example.valorantwiki.repository.MapRepository
import com.example.valorantwiki.ui.recyclerview.adapter.MapsAdapter
import com.example.valorantwiki.webclient.MapWebClient
import kotlinx.coroutines.launch


class MapsFragment : Fragment() {

    private val binding by lazy { FragmentMapsBinding.inflate(layoutInflater) }
    private val repository by lazy {
        MapRepository(
            requireContext(),
            MapWebClient()
        )
    }
    private val adapter by lazy { MapsAdapter(requireContext()) }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setsUpRecyclerView()
        setsUpRefreshButton()
        loadMaps()
    }

    private fun setsUpRefreshButton() {
        binding.fabRefreshMaps.setOnClickListener {
            loadMaps()
        }
    }

    private fun loadMaps() {
        lifecycleScope.launch {
            binding.errorMessageMaps.visibility = View.GONE
            binding.progressbarMapsFragment.visibility = View.VISIBLE
            repository.getAll()?.let { maps ->
                adapter.addAll(maps)
            } ?: showErrorMessage()
            binding.progressbarMapsFragment.visibility = View.GONE
        }
    }

    private fun showErrorMessage() {
        binding.errorMessageMaps.visibility = View.VISIBLE
    }

    private fun setsUpRecyclerView() {
        binding.recyclerviewMaps.adapter = adapter
    }
}