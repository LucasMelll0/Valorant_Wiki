package com.example.valorantwiki.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
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
        /*setHasOptionsMenu(true)*/
        setsUpRecyclerView()
        setsUpRefreshButton()
        loadMaps()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setsUpMenuProvider()
    }

    private fun setsUpMenuProvider() {
        activity?.let {
            val menuHost: MenuHost = it
            menuHost.invalidateMenu()
            menuHost.addMenuProvider(object : MenuProvider {
                override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                    menuInflater.inflate(R.menu.activity_main_menu, menu)
                }

                override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                    return when (menuItem.itemId) {
                        R.id.action_search -> {
                            Log.i("Test", "onMenuItemSelected: Entrou")
                            val search = menuItem.actionView as? SearchView
                            setsUpSearchView(search)
                            true
                        }
                        else -> false
                    }
                }
            }, viewLifecycleOwner, Lifecycle.State.RESUMED)
        }
    }

    /*@Deprecated("Deprecated in Java")
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
    }*/

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
            errorMessage(false)
            progressBar(true)
            viewModel.getAll()
            viewModel.mapsLiveData.observe(this@MapsFragment) { maps ->
                progressBar(false)
                if (maps.isNotEmpty()) {
                    errorMessage(false)
                    adapter.submitList(maps)
                } else {
                    if (adapter.currentList.isEmpty()) {
                        errorMessage(true)
                    }
                }
            }
        }
    }

    private fun progressBar(visible: Boolean) {
        binding.progressbarMapsFragment.visibility = if (visible) View.VISIBLE else View.GONE
    }

    private fun errorMessage(visible: Boolean) {
        binding.errorMessageMaps.visibility = if (visible) View.VISIBLE else View.GONE
    }

    private fun setsUpRecyclerView() {
        binding.recyclerviewMaps.adapter = adapter
    }
}