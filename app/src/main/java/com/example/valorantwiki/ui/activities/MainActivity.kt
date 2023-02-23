package com.example.valorantwiki.ui.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.valorantwiki.R
import com.example.valorantwiki.databinding.ActivityMainBinding
import com.example.valorantwiki.ui.fragments.AgentsFragment
import com.example.valorantwiki.ui.fragments.MapsFragment
import com.example.valorantwiki.ui.fragments.WeaponsFragment
import com.example.valorantwiki.ui.viewPager.ViewPagerAdapter
import com.example.valorantwiki.viewmodel.agentviewmodel.AgentListViewModel
import com.example.valorantwiki.viewmodel.mapviewmodel.MapListViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val agentListViewModel: AgentListViewModel by viewModel()
    private val mapListViewModel: MapListViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setsUpToolBar()
        getData()

    }

    private fun setsUpToolBar() {
        val toolbar = binding.toolbarMainActivity
        setSupportActionBar(toolbar)
        supportActionBar?.title = null
    }


    private fun getData() {
        lifecycleScope.launch {
            binding.splashScreenLoading.visibility = View.VISIBLE
            val language = getString(R.string.linguagem)
            agentListViewModel.getAll(language)
            mapListViewModel.getAll()
            setsUpTabLayout()
            binding.splashScreenLoading.visibility = View.GONE

        }
    }

    private fun setsUpTabLayout() {
        val adapter = ViewPagerAdapter(this)
        binding.apply {
            viewPager.adapter = adapter
            adapter.addFragment(AgentsFragment(), getString(R.string.agentes))
            adapter.addFragment(MapsFragment(), getString(R.string.mapas))
            adapter.addFragment(WeaponsFragment(), getString(R.string.armas))
            viewPager.offscreenPageLimit = adapter.itemCount
            val mediator =
                TabLayoutMediator(tabLayout, viewPager) { tab: TabLayout.Tab, position: Int ->
                    tab.text = adapter.getTitle(position)
                }
            mediator.attach()
        }
    }
}