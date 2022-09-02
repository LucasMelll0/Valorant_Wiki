package com.example.valorantwiki.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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
        configuraTabLayout()
    }

    private fun configuraTabLayout(){
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