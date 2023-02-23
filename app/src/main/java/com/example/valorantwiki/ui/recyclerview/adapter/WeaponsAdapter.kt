package com.example.valorantwiki.ui.recyclerview.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.valorantwiki.databinding.WeaponItemBinding
import com.example.valorantwiki.model.Weapon

class WeaponsAdapter(
    var onClick: (id: String) -> Unit = {}
) : ListAdapter<Weapon, WeaponsAdapter.WeaponsViewHolder>(differCallBack) {

    companion object {
        private val differCallBack = object : DiffUtil.ItemCallback<Weapon>() {
            override fun areItemsTheSame(oldItem: Weapon, newItem: Weapon): Boolean {
                return oldItem.uuid == newItem.uuid
            }

            override fun areContentsTheSame(oldItem: Weapon, newItem: Weapon): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class WeaponsViewHolder(private val binding: WeaponItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

            fun bindItem(weapon: Weapon) {
                val imageView = binding.imageviewWeaponImageItem
                val textViewName = binding.textviewWeaponNameItem
                imageView.load(weapon.image)
                textViewName.text = weapon.name
                binding.root.setOnClickListener {
                    onClick(weapon.uuid)
                }
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeaponsViewHolder {
        val binding =
            WeaponItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WeaponsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WeaponsViewHolder, position: Int) {
        holder.bindItem(getItem(position))
    }
}