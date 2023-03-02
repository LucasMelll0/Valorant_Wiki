package com.example.valorantwiki.ui.recyclerview.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.valorantwiki.databinding.GalleryItemBinding

class GalleryAdapter(
) : ListAdapter<String, GalleryAdapter.PlacesViewHolder>(differCallBack) {

    companion object {
        private val differCallBack = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }
        }
    }
    class PlacesViewHolder(private val binding: GalleryItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindItem(image: String){
            binding.apply {
                val imageViewGallery = binding.imageviewGallery
                imageViewGallery.load(image)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlacesViewHolder {
        val binding = GalleryItemBinding.inflate(LayoutInflater.from(parent.context), parent,  false)

        return PlacesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PlacesViewHolder, position: Int) {
        holder.bindItem(getItem(position))
    }
}