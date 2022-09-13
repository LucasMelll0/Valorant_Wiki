package com.example.valorantwiki.ui.recyclerview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.valorantwiki.databinding.LocationItemBinding
import com.example.valorantwiki.webclient.webClientModel.Place

class PlacesAdapter(
    val context: Context,
    placesList: List<Place> = emptyList()
) : RecyclerView.Adapter<PlacesAdapter.PlacesViewHolder>() {

    private val dataSet : MutableList<Place> = placesList.toMutableList()


    class PlacesViewHolder(private val binding: LocationItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindItem(place: Place){
            binding.apply {
                textviewPlaceLocationItem.text = place.regionName
                textviewRegionLocationItem.text = place.superRegionName
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlacesViewHolder {
        val binding = LocationItemBinding.inflate(LayoutInflater.from(context), parent,  false)

        return PlacesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PlacesViewHolder, position: Int) {
        holder.bindItem(dataSet[position])
    }

    override fun getItemCount(): Int = dataSet.size
}