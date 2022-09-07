package com.example.valorantwiki.ui.activities

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import coil.load
import com.example.valorantwiki.databinding.ActivityAgentBinding
import com.example.valorantwiki.model.Agent
import com.example.valorantwiki.repository.AgentRepository
import com.example.valorantwiki.ui.activities.extensions.formatStrToColorStr
import com.example.valorantwiki.ui.dialogs.AbilityDialog
import com.example.valorantwiki.ui.recyclerview.adapter.AbilitiesAdapter
import com.example.valorantwiki.webclient.AgentWebClient
import kotlinx.coroutines.launch

class AgentActivity : AppCompatActivity() {

    private val binding by lazy { ActivityAgentBinding.inflate(layoutInflater) }
    private val repository by lazy { AgentRepository(this, AgentWebClient()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        tryToGetAgent()
        setsUpButtonBack()
    }

    private fun setsUpButtonBack() {
        binding.imagebuttonBackAgentActivity.setOnClickListener {
            finish()
        }
    }

    private fun tryToGetAgent() {
        lifecycleScope.launch {
            intent.extras?.getString(AGENT_UUID)?.let { uuid ->
                binding.progressbarAgentActivity.visibility = View.VISIBLE
                repository.getById(uuid)?.let { agent ->
                    fillFields(agent)
                    binding.progressbarAgentActivity.visibility = View.GONE
                } ?: finish()

            }

        }
    }

    private fun fillFields(agent: Agent) {
        binding.apply {
            val backgroundColorFormated = agent.backgrondColor.formatStrToColorStr()
            cardviewToolbarAgentActivity.setCardBackgroundColor(Color.parseColor("#${backgroundColorFormated}"))
            window.statusBarColor = Color.parseColor("#${backgroundColorFormated}")
            imageviewPortraitAgentActivity.load(agent.image)
            textviewNameAgentActivity.text = agent.name
            textviewClassAgentActivity.text = agent.role.displayName
            textviewClassDescriptionAgentActivity.text = agent.role.description
            textviewAgentDescriptionAgentActivity.text = agent.description
            setsUpAbilitiesList(backgroundColorFormated, agent)
        }
    }

    private fun setsUpAbilitiesList(
        backgroundColorFormated: String,
        agent: Agent
    ) {
        binding.recyclerviewAbilitiesAgentActivity.adapter = AbilitiesAdapter(
            context = this@AgentActivity,
            backgroundColor = backgroundColorFormated,
            abilityList = agent.abilities
        ) { ability ->
            AbilityDialog(ability).show(supportFragmentManager, null)
        }
    }
}