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
import com.example.valorantwiki.databinding.FragmentAgentsBinding
import com.example.valorantwiki.ui.recyclerview.adapter.AgentsAdapter
import com.example.valorantwiki.viewmodel.agentviewmodel.AgentListViewModel
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class AgentsFragment : Fragment() {

    private val binding: FragmentAgentsBinding by lazy {
        FragmentAgentsBinding.inflate(
            layoutInflater
        )
    }
    private val adapter: AgentsAdapter by inject()
    private val viewModel: AgentListViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setsUpBottomNavigation()
        setsUpRecyclerView()
        setsUpRefreshButton()
        loadAgents()
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
                    return when(menuItem.itemId){
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

    private fun setsUpSearchView(searchView: SearchView?) {
        searchView?.let {
            searchView.isSubmitButtonEnabled = false
            searchView.setOnCloseListener {
                loadAgents()
                false
            }
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    newText?.let {
                        if (newText.isNotEmpty()) {
                            val filteredList = viewModel.searchByName(newText)
                            Log.i("Test", "onQueryTextChange: $filteredList")
                            adapter.submitList(filteredList)

                        } else {
                            loadAgents()
                        }

                    } ?: loadAgents()
                    return false
                }

            })
        }
    }

    private fun setsUpRefreshButton() {
        binding.fabRefreshAgents.setOnClickListener {
            loadAgents()
        }
    }

    private fun loadAgents() {
        lifecycleScope.launch {
            errorMessage(false)
            progressBar(true)
            val language = getString(R.string.linguagem)
            viewModel.getAll(language)
            var roleName = viewModel.getAgentRoleFilter()
            var agents = viewModel.getByRoleName(roleName)
            adapter.submitList(agents)
            viewModel.agentsLiveData.observe(this@AgentsFragment) {
                progressBar(false)
                if (it.isNotEmpty()) {
                    roleName = viewModel.getAgentRoleFilter()
                    agents = viewModel.getByRoleName(roleName)
                    adapter.submitList(agents)
                } else {
                    if (adapter.currentList.isEmpty()) {
                        errorMessage(true)
                    }
                }
            }
        }
    }

    private fun progressBar(visible: Boolean) {
        binding.progressbarAgentsFragment.visibility = if (visible) View.VISIBLE else View.GONE
    }

    private fun errorMessage(visible: Boolean) {
        binding.errorMessageAgents.visibility = if (visible) View.VISIBLE else View.GONE
    }

    private fun setsUpRecyclerView() {
        binding.recyclerviewAgents.adapter = adapter
    }

    private fun setsUpBottomNavigation() {
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.page_all -> {
                    viewModel.setAgentRoleFilter(null)
                    applyFilterByRoleId(null)
                    true
                }
                R.id.page_initiator -> {
                    val role = getString(R.string.iniciador)
                    viewModel.setAgentRoleFilter(role)
                    applyFilterByRoleId(role)
                    true
                }
                R.id.page_controller -> {
                    val role = getString(R.string.controlador)
                    viewModel.setAgentRoleFilter(role)
                    applyFilterByRoleId(role)
                    true
                }
                R.id.page_sentinel -> {
                    val role = getString(R.string.sentinela)
                    viewModel.setAgentRoleFilter(role)
                    applyFilterByRoleId(role)
                    true
                }
                R.id.page_duelist -> {
                    val role = getString(R.string.duelista)
                    viewModel.setAgentRoleFilter(role)
                    applyFilterByRoleId(role)
                    true
                }
                else -> false
            }
        }
    }

    private fun applyFilterByRoleId(roleName: String?) {
        viewModel.agentsLiveData.observe(this) {
            val agents = viewModel.getByRoleName(roleName)
            if (agents.isNotEmpty()) {
                adapter.submitList(agents)
                errorMessage(false)
            } else {
                if (adapter.currentList.isEmpty()) {
                    errorMessage(true)
                }
            }
        }

    }
}