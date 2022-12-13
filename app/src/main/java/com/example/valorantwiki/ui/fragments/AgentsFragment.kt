package com.example.valorantwiki.ui.fragments

import android.os.Bundle
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
                        viewModel.agentsLiveData.value?.let { agents ->
                            val filteredList = agents.filter { agent ->
                                agent.name.startsWith(newText, true)
                            }
                            adapter.submitList(filteredList)
                        }

                    }
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
                viewModel.agentsLiveData.observe(this@AgentsFragment) {
                    if (it.isNotEmpty()) {
                        val roleId = binding.bottomNavigation.selectedItemId
                        val agents = viewModel.getForSelectedRole(roleId)
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
                    viewModel.agentsLiveData.observe(this) { agents ->
                        if (agents.isNotEmpty()) {
                            adapter.submitList(agents)
                        } else {
                            if (adapter.currentList.isEmpty()) {
                                showErroMessage()
                            }
                        }
                    }
                    true
                }
                R.id.page_initiator -> {
                    viewModel.agentsLiveData.observe(this) {
                        val agents = viewModel.getInitiators()
                        if (agents.isNotEmpty()) {
                            adapter.submitList(agents)
                        } else {
                            if (adapter.currentList.isEmpty()) {
                                showErroMessage()
                            }
                        }
                    }

                    true
                }
                R.id.page_controller -> {
                    viewModel.agentsLiveData.observe(this) {
                        val agents = viewModel.getControlers()
                        if (agents.isNotEmpty()) {
                            adapter.submitList(agents)
                        } else {
                            if (adapter.currentList.isEmpty()) {
                                showErroMessage()
                            }
                        }
                    }
                    true
                }
                R.id.page_sentinel -> {
                    viewModel.agentsLiveData.observe(this) {
                        val agents = viewModel.getSentinels()
                        if (agents.isNotEmpty()) {
                            adapter.submitList(agents)
                        } else {
                            if (adapter.currentList.isEmpty()) {
                                showErroMessage()
                            }
                        }
                    }
                    true
                }
                R.id.page_duelist -> {
                    viewModel.agentsLiveData.observe(this) {
                        val agents = viewModel.getDuelists()
                        if (agents.isNotEmpty()) {
                            adapter.submitList(agents)
                        } else {
                            if (adapter.currentList.isEmpty()) {
                                showErroMessage()
                            }
                        }
                    }
                    true
                }
                else -> false
            }
        }
    }


}