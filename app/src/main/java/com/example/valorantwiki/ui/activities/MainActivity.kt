package com.example.valorantwiki.ui.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.valorantwiki.R
import com.example.valorantwiki.databinding.ActivityMainBinding
import com.example.valorantwiki.repository.AgentRepository
import com.example.valorantwiki.ui.fragments.AgentsFragment
import com.example.valorantwiki.ui.fragments.MapsFragment
import com.example.valorantwiki.ui.viewPager.ViewPagerAdapter
import com.example.valorantwiki.webclient.AgentWebClient
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.launch

const val TAG = "Testes MainActivity"
class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setsUpTabLayout()
        getAgentsForFirstInit()

    }

    private fun getAgentsForFirstInit() {
        lifecycleScope.launch {
            binding.splashScreenLoading.visibility =View.VISIBLE
            AgentRepository(this@MainActivity, AgentWebClient()).getAll()
            binding.splashScreenLoading.visibility =View.GONE
        }
    }

    private fun setsUpTabLayout(){
        val adapter = ViewPagerAdapter(this)
        binding.apply {
            viewPager.adapter = adapter
            adapter.addFragment(AgentsFragment(), getString(R.string.agentes))
            adapter.addFragment(MapsFragment(), getString(R.string.mapas))
            viewPager.offscreenPageLimit = adapter.itemCount
            val mediator = TabLayoutMediator(tabLayout, viewPager){ tab: TabLayout.Tab, position:Int ->
                tab.text = adapter.getTitle(position)
            }
            mediator.attach()
        }
    }
}