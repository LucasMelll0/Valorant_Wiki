package com.example.valorantwiki.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.valorantwiki.R
import com.example.valorantwiki.databinding.FragmentAgentsBinding
import com.example.valorantwiki.repository.AgentRepository
import com.example.valorantwiki.ui.recyclerview.adapter.AgentsAdapter
import com.example.valorantwiki.viewmodel.agentlistviewmodel.AgentListViewModel
import com.example.valorantwiki.viewmodel.agentlistviewmodel.AgentListViewModelFactory
import com.example.valorantwiki.webclient.AgentWebClient
import kotlinx.coroutines.launch

class AgentsFragment : Fragment() {

    private val binding by lazy { FragmentAgentsBinding.inflate(layoutInflater) }
    private val adapter by lazy { AgentsAdapter(requireContext()) }
    private lateinit var viewModel: AgentListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        setsUpViewModel()
        setsUpBottomNavigation()
        setsUpRecyclerView()
        setsUpRefreshButton()
        loadAgents()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.activity_main_menu, menu)
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
                            viewModel.agentsLiveData.value?.let { agents ->
                                val filteredList = agents.filter { agent ->
                                    agent.name.startsWith(newText, true)
                                }
                                adapter.submitList(filteredList)
                            }
                        } else {
                            loadAgents()
                        }

                    } ?: loadAgents()
                    return false
                }

            })
        }
    }

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


    private fun setsUpViewModel() {
        val application = requireActivity().application
        val agentWebClient = AgentWebClient()
        val viewModelFactory = AgentListViewModelFactory(
            application,
            AgentRepository(agentWebClient)
        )
        viewModel =
            ViewModelProvider(viewModelStore, viewModelFactory)[AgentListViewModel::class.java]
    }

    private fun setsUpRefreshButton() {
        binding.fabRefreshAgents.setOnClickListener {
            loadAgents()
        }
    }

    private fun loadAgents() {
        lifecycleScope.launch {
            binding.errorMessageAgents.visibility = View.GONE
            binding.progressbarAgentsFragment.visibility = View.VISIBLE
            viewModel.getAll()
            viewModel.agentsLiveData.value?.let {
                Log.i("Test", "loadAgents: True")
                var roleId = binding.bottomNavigation.selectedItemId
                var agents = viewModel.getForSelectedRole(roleId)
                adapter.submitList(agents)
                viewModel.agentsLiveData.observe(this@AgentsFragment) {
                    if (it.isNotEmpty()) {
                        roleId = binding.bottomNavigation.selectedItemId
                        agents = viewModel.getForSelectedRole(roleId)
                        adapter.submitList(agents)
                    } else {
                        if (adapter.currentList.isEmpty()) {
                            showErroMessage()
                        }
                    }
                }
            } ?: showErroMessage()
            binding.progressbarAgentsFragment.visibility = View.GONE
        }
    }

    private fun showErroMessage() {
        binding.errorMessageAgents.visibility = View.VISIBLE
    }

    private fun setsUpRecyclerView() {
        binding.recyclerviewAgents.adapter = adapter
    }

    private fun setsUpBottomNavigation() {
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.page_all -> {
                    applyFilterByRoleId(item.itemId)
                    true
                }
                R.id.page_initiator -> {
                    applyFilterByRoleId(item.itemId)
                    true
                }
                R.id.page_controller -> {
                    applyFilterByRoleId(item.itemId)
                    true
                }
                R.id.page_sentinel -> {
                    applyFilterByRoleId(item.itemId)
                    true
                }
                R.id.page_duelist -> {
                    applyFilterByRoleId(item.itemId)
                    true
                }
                else -> false
            }
        }
    }

    private fun applyFilterByRoleId(roleId: Int) {
        viewModel.agentsLiveData.value?.let {
            viewModel.agentsLiveData.observe(this) {
                val agents = viewModel.getForSelectedRole(roleId)
                if (agents.isNotEmpty()) {
                    adapter.submitList(agents)
                } else {
                    if (adapter.currentList.isEmpty()) {
                        showErroMessage()
                    }
                }
            }
        } ?: showErroMessage()
    }


}