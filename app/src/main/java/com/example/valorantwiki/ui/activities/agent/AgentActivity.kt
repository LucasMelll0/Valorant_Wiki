package com.example.valorantwiki.ui.activities.agent

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import coil.load
import com.example.valorantwiki.databinding.ActivityAgentBinding
import com.example.valorantwiki.model.Agent
import com.example.valorantwiki.ui.activities.AGENT_UUID
import com.example.valorantwiki.ui.activities.extensions.formatStrToColorStr
import com.example.valorantwiki.ui.dialogs.AbilityDialog
import com.example.valorantwiki.ui.recyclerview.adapter.AbilitiesAdapter
import com.example.valorantwiki.viewmodel.agentviewmodel.AgentViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class AgentActivity : AppCompatActivity() {

    private val binding by lazy { ActivityAgentBinding.inflate(layoutInflater) }
    private val viewModel: AgentViewModel by viewModel()

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
                viewModel.getById(uuid)
                viewModel.agentLiveData.observe(this@AgentActivity) { agent ->
                    fillFields(agent)
                }
                binding.progressbarAgentActivity.visibility = View.GONE
            } ?: finish()

        }

    }

    private fun fillFields(agent: Agent) {
        binding.apply {
            val backgroundColorFormatted = agent.backgrondColor.formatStrToColorStr()
            cardviewToolbarAgentActivity.setCardBackgroundColor(Color.parseColor("#${backgroundColorFormatted}"))
            window.statusBarColor = Color.parseColor("#${backgroundColorFormatted}")
            imageviewBackgroundAgentActivity.load(agent.background)
            imageviewPortraitAgentActivity.load(agent.image)
            textviewNameAgentActivity.text = agent.name
            textviewClassAgentActivity.text = agent.role.displayName
            textviewClassDescriptionAgentActivity.text = agent.role.description
            textviewAgentDescriptionAgentActivity.text = agent.description
            setsUpAbilitiesList(backgroundColorFormatted, agent)
        }
    }

    private fun setsUpAbilitiesList(
        backgroundColorFormatted: String,
        agent: Agent
    ) {
        binding.recyclerviewAbilitiesAgentActivity.adapter = AbilitiesAdapter(
            context = this@AgentActivity,
            backgroundColor = backgroundColorFormatted,
            abilityList = agent.abilities
        ) { ability ->
            AbilityDialog(ability).show(supportFragmentManager, AbilityDialog.TAG)


        }
    }
}