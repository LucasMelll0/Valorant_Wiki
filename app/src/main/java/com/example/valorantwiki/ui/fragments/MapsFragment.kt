package com.example.valorantwiki.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.valorantwiki.R
import com.example.valorantwiki.databinding.FragmentMapsBinding
import com.example.valorantwiki.ui.recyclerview.adapter.MapsAdapter
import com.example.valorantwiki.viewmodel.maplistviewmodel.MapListViewModel
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class MapsFragment : Fragment() {

    private val binding by lazy { FragmentMapsBinding.inflate(layoutInflater) }
    private val viewModel: MapListViewModel by viewModel()
    private val adapter: MapsAdapter by inject()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        setsUpRecyclerView()
        setsUpRefreshButton()
        loadMaps()
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.activity_main_menu, menu)
    }

    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_search -> {
                val search = item.actionView as? SearchView
                setsUpSearchView(search)
                true
            }
            else -> false
        }
    }

    private fun setsUpSearchView(searchView: SearchView?) {
        searchView?.let {
            searchView.isSubmitButtonEnabled = false
            searchView.setOnCloseListener {
                loadMaps()
                false
            }
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    newText?.let {
                        viewModel.mapsLiveData.value?.let { maps ->
                            val filteredList = maps.filter { map ->
                                map.name.startsWith(newText, true)
                            }
                            adapter.submitList(filteredList)
                        }

                    }
                    return false
                }
            })
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