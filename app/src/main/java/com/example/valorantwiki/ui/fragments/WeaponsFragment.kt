package com.example.valorantwiki.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.valorantwiki.R
import com.example.valorantwiki.databinding.FragmentWeaponsBinding
import com.example.valorantwiki.ui.activities.WEAPON_UUID
import com.example.valorantwiki.ui.activities.extensions.goTo
import com.example.valorantwiki.ui.activities.weapon.WeaponActivity
import com.example.valorantwiki.ui.recyclerview.adapter.WeaponsAdapter
import com.example.valorantwiki.viewmodel.weaponViewModel.WeaponListViewModel
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class WeaponsFragment : Fragment() {

    private val viewModel: WeaponListViewModel by viewModel()
    private val adapter: WeaponsAdapter by inject()

    private val binding by lazy {
        FragmentWeaponsBinding.inflate(layoutInflater)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setsUpMenuProvider()
        setsUpRecyclerViewWeapons()
    }

    private fun setsUpRecyclerViewWeapons() {
        context?.let { context ->
            val recyclerviewWeapons = binding.recyclerviewWeapons
            recyclerviewWeapons.adapter = adapter
            recyclerviewWeapons.layoutManager = LinearLayoutManager(context)
            adapter.onClick = {
                context.goTo(WeaponActivity::class.java) {
                    putExtra(WEAPON_UUID, it)
                }
            }
        }
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
                    return when(menuItem.itemId) {
                        R.id.action_search -> {
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

    private fun setsUpSearchView(search: SearchView?) {
        search?.let {
            search.isSubmitButtonEnabled = false
            search.setOnCloseListener {
                loadWeapons()
                false
            }
            search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    newText?.let {
                        val filteredList = viewModel.searchByName(it)
                        adapter.submitList(filteredList)

                    } ?: loadWeapons()
                    return false
                }
            })
        }
    }

    private fun loadWeapons() {
        lifecycleScope.launch {
            errorMessage(false)
            progressBar(true)
            val language = getString(R.string.linguagem)
            viewModel.getAll(language)
            viewModel.weapons.observe(this@WeaponsFragment) {
                progressBar(false)
                if (it.isNotEmpty()) {
                    errorMessage(false)
                    adapter.submitList(it)
                }else {
                    if (adapter.currentList.isEmpty()){
                        errorMessage(true)
                    }
                }
            }
        }
    }

    private fun progressBar(visible: Boolean) {
        val progressBar = binding.progressbarWeaponsFragment
        progressBar.visibility = if (visible) View.VISIBLE else View.GONE
    }

    private fun errorMessage(visible: Boolean) {
        val errorMessage = binding.errorMessageWeapons
        errorMessage.visibility = if (visible) View.VISIBLE else View.GONE
    }

    override fun onStart() {
        super.onStart()
        loadWeapons()

    }
}