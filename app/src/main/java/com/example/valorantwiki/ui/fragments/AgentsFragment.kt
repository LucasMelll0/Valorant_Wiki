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
    private val repository by lazy { AgentRepository(AgentWebClient()) }

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
    }

    override fun onResume() {
        super.onResume()
        lifecycleScope.launch {
            val agents = repository.buscaTodos()
            adapter.addAll(agents)
        }
    }

    private fun setsUpRecyclerView() {
        binding.recyclerviewAgents.adapter = adapter
    }

    private fun setsUpBottomNavigation() {
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.page_all -> {
                    println("all")
                    true
                }
                R.id.page_initiator -> {
                    println("initiator")
                    true
                }
                R.id.page_controller -> {
                    println("controler")
                    true
                }
                R.id.page_sentinel -> {
                    println("sentinel")
                    true
                }
                R.id.page_duelist -> {
                    println("duelist")
                    true
                }
                else -> false
            }
        }
    }


}