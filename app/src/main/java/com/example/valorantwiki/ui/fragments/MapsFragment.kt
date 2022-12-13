package com.example.valorantwiki.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.valorantwiki.databinding.FragmentMapsBinding
import com.example.valorantwiki.repository.MapRepository
import com.example.valorantwiki.ui.recyclerview.adapter.MapsAdapter
import com.example.valorantwiki.viewmodel.maplistviewmodel.MapListViewModel
import com.example.valorantwiki.viewmodel.maplistviewmodel.MapListViewModelFactory
import com.example.valorantwiki.webclient.MapWebClient
import kotlinx.coroutines.launch


class MapsFragment : Fragment() {

    private val binding by lazy { FragmentMapsBinding.inflate(layoutInflater) }
    private lateinit var viewModel: MapListViewModel
    private val adapter by lazy { MapsAdapter(requireContext()) }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setsUpViewModel()
        setsUpRecyclerView()
        setsUpRefreshButton()
        loadMaps()
    }

    private fun setsUpViewModel() {
        val application = requireActivity().application
        val mapWebClient = MapWebClient()
        val viewModelFactory = MapListViewModelFactory(
            application,
            MapRepository(mapWebClient)
        )
        viewModel =
            ViewModelProvider(viewModelStore, viewModelFactory)[MapListViewModel::class.java]
    }

    override fun onResume() {
        super.onResume()
        lifecycleScope.launch {
            viewModel.getAll()
        }
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
            viewModel.getAll()
            viewModel.mapsLiveData.value?.let {
                viewModel.mapsLiveData.observe(this@MapsFragment) { maps ->
                    if (maps.isNotEmpty()) {
                        adapter.submitList(maps)
                    } else {
                        if (adapter.currentList.isEmpty()) {
                            showErrorMessage()
                        }
                    }
                }
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