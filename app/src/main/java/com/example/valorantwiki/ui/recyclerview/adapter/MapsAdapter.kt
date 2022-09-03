package com.example.valorantwiki.ui.recyclerview.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.valorantwiki.databinding.MapItemBinding
import com.example.valorantwiki.model.Map

class MapsAdapter(
    private val context: Context,
    maps : List<Map> = emptyList()
    ) : RecyclerView.Adapter<MapsAdapter.MapsViewHolder>() {

    private val dataSet: MutableList<Map> = maps.toMutableList()

    class MapsViewHolder(private val binding: MapItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindItem(map: Map){
            binding.apply {
                mapImageItem.load(map.image)
                mapNameItem.text = map.name
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MapsViewHolder {
        val binding = MapItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return MapsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MapsViewHolder, position: Int) {
        holder.bindItem(dataSet[position])
    }

    override fun getItemCount(): Int = dataSet.size

    @SuppressLint("NotifyDataSetChanged")
    fun addAll(maps: List<Map>) {
        dataSet.clear()
        dataSet.addAll(maps)
        notifyDataSetChanged()
    }
}