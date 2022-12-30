package com.example.valorantwiki.ui.recyclerview.adapter

import android.content.Context
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.valorantwiki.databinding.MapItemBinding
import com.example.valorantwiki.model.Map
import com.example.valorantwiki.ui.activities.MAP_UUID
import com.example.valorantwiki.ui.activities.MapActivity
import com.example.valorantwiki.ui.activities.extensions.goTo

class MapsAdapter(
    private val context: Context,
    ) : ListAdapter<Map, MapsAdapter.MapsViewHolder>(differCallBack) {

    companion object {
        private val differCallBack = object :
            DiffUtil.ItemCallback<Map>(){
            override fun areItemsTheSame(oldItem: Map, newItem: Map): Boolean {
                return oldItem.uuid == newItem.uuid
            }

            override fun areContentsTheSame(oldItem: Map, newItem: Map): Boolean {
                return oldItem == newItem
            }

        }
    }

    inner class MapsViewHolder(private val binding: MapItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindItem(map: Map){
            binding.apply {
                mapImageItem.load(map.image)
                mapNameItem.text = map.name
            }
            itemView.setOnClickListener {
                context.goTo(MapActivity::class.java){
                    flags = FLAG_ACTIVITY_NEW_TASK
                    putExtra(MAP_UUID, map.uuid)
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MapsViewHolder {
        val binding = MapItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return MapsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MapsViewHolder, position: Int) {
        holder.bindItem(getItem(position))
    }

}