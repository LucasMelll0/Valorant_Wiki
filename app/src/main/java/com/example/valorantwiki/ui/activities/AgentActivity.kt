package com.example.valorantwiki.ui.activities

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import coil.load
import com.example.valorantwiki.databinding.ActivityAgentBinding
import com.example.valorantwiki.model.Agent
import com.example.valorantwiki.repository.AgentRepository
import com.example.valorantwiki.ui.activities.extensions.formatStrToColorStr
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
                repository.getById(uuid)?.let { agent ->
                    binding.apply {
                        fillFields(agent)
                    }
                } ?: finish()
            }

        }
    }

    private fun ActivityAgentBinding.fillFields(agent: Agent) {
        val backgroundColorFormated = agent.backgrondColor.formatStrToColorStr()
        cardviewToolbarAgentActivity.setCardBackgroundColor(Color.parseColor("#${backgroundColorFormated}"))
        window.statusBarColor = Color.parseColor("#${backgroundColorFormated}")
        imageviewPortraitAgentActivity.load(agent.image)
        textviewNameAgentActivity.text = agent.name
        textviewClassAgentActivity.text = agent.role.displayName
        textviewClassDescriptionAgentActivity.text = agent.role.description
        textviewAgentDescriptionAgentActivity.text = agent.description
    }
}