package com.example.valorantwiki.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.example.valorantwiki.databinding.ActivityMainBinding
import com.example.valorantwiki.repository.AgentRepository
import com.example.valorantwiki.webclient.AgentWebClient
import kotlinx.coroutines.launch

const val TAG = "Testes MainActivity"
class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        lifecycleScope.launch {
            AgentRepository(
                webClient = AgentWebClient()
            ).apply {
                val agents = buscaTodos()
                Log.i(TAG, "onCreate: $agents")
            }
        }
    }
}