package com.example.valorantwiki.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.valorantwiki.databinding.ActivityAgentBinding

class AgentActivity : AppCompatActivity() {

    private val binding by lazy { ActivityAgentBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}