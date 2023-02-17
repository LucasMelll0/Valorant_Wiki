package com.example.valorantwiki.ui.activities.map

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import coil.load
import com.example.valorantwiki.R
import com.example.valorantwiki.databinding.ActivityMapBinding
import com.example.valorantwiki.model.Map
import com.example.valorantwiki.ui.activities.MAP_UUID
import com.example.valorantwiki.ui.recyclerview.adapter.PlacesAdapter
import com.example.valorantwiki.viewmodel.mapviewmodel.MapViewModel
import com.example.valorantwiki.webclient.webClientModel.Place
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MapActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMapBinding.inflate(layoutInflater) }
    private val viewmodel: MapViewModel by viewModel()

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
                viewmodel.getById(uuid)
                viewmodel.mapLiveData.observe(this@MapActivity) { map ->
                    fillFields(map)
                }
                binding.progressbarMapActivity.visibility = View.GONE

            }
        } ?: finish()
    }

    private fun fillFields(map: Map) {
        binding.apply {
            imageviewPortraitMapActivity.load(map.image)
            textviewNameMapActivity.text = map.name
            setsUpMiniMapImage(map.miniMap)
            setsUpPlacesList(map.places)
        }
    }

    private fun setsUpMiniMapImage(image: String) {
        image.isBlank().let {
            if (it) {
                binding.minimapTitle.text = getString(R.string.nao_tem_minimapa)
                binding.cardviewMinimap.visibility = View.GONE
            } else {
                binding.imageviewMinimapMapActivity.load(image)
            }
        }
    }

    private fun setsUpPlacesList(places: List<Place>) {
        places.isEmpty().let {
            if (it) {
                binding.placesTitle.visibility = View.GONE
                binding.recyclerviewPlacesMapActivity.visibility = View.GONE
            } else {
                val adapter = PlacesAdapter(this, places)
                binding.recyclerviewPlacesMapActivity.adapter = adapter

            }
        }
    }
}