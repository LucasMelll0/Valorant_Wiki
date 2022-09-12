package com.example.valorantwiki.ui.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import coil.load
import com.example.valorantwiki.databinding.ActivityMapBinding
import com.example.valorantwiki.model.Map
import com.example.valorantwiki.repository.MapRepository
import com.example.valorantwiki.webclient.MapWebClient
import kotlinx.coroutines.launch

class MapActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMapBinding.inflate(layoutInflater) }
    private val repository by lazy { MapRepository(this, MapWebClient()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        tryToGetMap()
        setsUpbackButton()
    }

    private fun setsUpbackButton() {
        binding.imagebuttonBackMapActivity.setOnClickListener {
            finish()
        }
    }

    private fun tryToGetMap() {
        intent.getStringExtra(MAP_UUID)?.let { uuid ->
            lifecycleScope.launch {
                binding.progressbarMapActivity.visibility = View.VISIBLE
                repository.getById(uuid)?.let { map ->
                    fillFields(map)
                    binding.progressbarMapActivity.visibility = View.GONE
                } ?: finish()
            }
        } ?: finish()
    }

    private fun fillFields(map: Map) {
        binding.apply {
            imageviewPortraitMapActivity.load(map.image)
            textviewNameMapActivity.text = map.name
            imageviewMinimapMapActivity.load(map.miniMap)
        }
    }
}