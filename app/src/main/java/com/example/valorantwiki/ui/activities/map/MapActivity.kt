package com.example.valorantwiki.ui.activities.map

import android.content.res.Resources
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import coil.load
import coil.transform.RoundedCornersTransformation
import com.example.valorantwiki.databinding.ActivityMapBinding
import com.example.valorantwiki.model.Map
import com.example.valorantwiki.ui.activities.MAP_UUID
import com.example.valorantwiki.ui.recyclerview.adapter.GalleryAdapter
import com.example.valorantwiki.viewmodel.mapviewmodel.MapViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MapActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMapBinding.inflate(layoutInflater) }
    private val viewModel: MapViewModel by viewModel()
    private val adapter: GalleryAdapter by inject()

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
                viewModel.getById(uuid)
                viewModel.mapLiveData.observe(this@MapActivity) { map ->
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
            textviewDescriptionMapActivity.text = map.description
            setsUpMiniMapImage(map.miniMap)
            setsUpButtonShowGallery(map.gallery)
        }
    }

    private fun setsUpButtonShowGallery(gallery: List<String>) {
        val viewPager = binding.viewpagerGallery
        val tabLayout = binding.tablayoutGallery
        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(
            MarginPageTransformer(
                (5 * Resources.getSystem()
                    .displayMetrics.density).toInt()
            )
        )
        compositePageTransformer.addTransformer { page, position ->
            val r = 1 - kotlin.math.abs(position)
            page.scaleY = (0.80f + r * 0.20f)
        }
        viewPager.also {
            it.clipChildren = true
            it.clipToPadding = false
            it.offscreenPageLimit = 3
            (it.getChildAt(0) as RecyclerView).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
            it.setPageTransformer(compositePageTransformer)
            it.adapter = this.adapter
        }
        adapter.submitList(gallery)
        // Conecta o viewPager ao TabLayout(dot indicator).
        val mediator = TabLayoutMediator(tabLayout, viewPager) { _: TabLayout.Tab, _: Int ->

        }.attach()
    }

    private fun setsUpMiniMapImage(image: String) {
        image.isBlank().let {
            if (it) {
                binding.cardviewMinimap.visibility = View.GONE
            } else {
                binding.imageviewMinimapMapActivity.load(image) {
                    transformations(RoundedCornersTransformation(50f))
                }
            }
        }
    }

}
