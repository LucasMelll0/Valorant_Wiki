package com.example.valorantwiki.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.valorantwiki.R
import com.example.valorantwiki.databinding.FragmentAgentsBinding
import com.example.valorantwiki.repository.AgentRepository
import com.example.valorantwiki.ui.recyclerview.adapter.AgentsAdapter
import com.example.valorantwiki.webclient.AgentWebClient
import kotlinx.coroutines.launch


class AgentsFragment : Fragment() {

    private val binding by lazy { FragmentAgentsBinding.inflate(layoutInflater) }
    private val adapter by lazy { AgentsAdapter(requireContext()) }
    private val repository by lazy {
        AgentRepository(
            requireContext(),
            AgentWebClient()
        )
    }

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
        getAgents()
    }

    private fun setsUpRefreshButton() {
        binding.fabRefreshAgents.setOnClickListener {
            getAgents()
        }
    }

    private fun getAgents() {
        lifecycleScope.launch {
            binding.errorMessageAgents.visibility = View.GONE
            binding.progressbarAgentsFragment.visibility = View.VISIBLE
            repository.getAll()?.let {
                adapter.addAll(it)
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
                    lifecycleScope.launch { adapter.addAll(repository.getAll() ?: emptyList()) }
                    true
                }
                R.id.page_initiator -> {
                    adapter.addAll(repository.getInitiators())
                    true
                }
                R.id.page_controller -> {
                    adapter.addAll(repository.getControllers())
                    true
                }
                R.id.page_sentinel -> {
                    adapter.addAll(repository.getSentinels())
                    true
                }
                R.id.page_duelist -> {
                    adapter.addAll(repository.getDuelists())
                    true
                }
                else -> false
            }
        }
    }


}