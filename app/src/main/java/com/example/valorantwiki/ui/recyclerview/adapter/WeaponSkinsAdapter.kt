package com.example.valorantwiki.ui.recyclerview.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.valorantwiki.R
import com.example.valorantwiki.databinding.WeaponSkinItemBinding
import com.example.valorantwiki.webclient.webClientModel.WeaponSkin

class WeaponSkinsAdapter : ListAdapter<WeaponSkin, WeaponSkinsAdapter.WeaponSkinViewHolder>(
    differCallBack
) {

    companion object {
        private val differCallBack = object : DiffUtil.ItemCallback<WeaponSkin>() {
            override fun areItemsTheSame(oldItem: WeaponSkin, newItem: WeaponSkin): Boolean {
                return oldItem.displayName == newItem.displayName
            }

            override fun areContentsTheSame(oldItem: WeaponSkin, newItem: WeaponSkin): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class WeaponSkinViewHolder(private val binding: WeaponSkinItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindItem(weaponSkin: WeaponSkin) {
            val imageViewSkin = binding.imageviewWeaponSkinItem
            val textViewName = binding.textviewNameWeaponSkinItem
            weaponSkin.apply {
                imageViewSkin.load(displayIcon)
                textViewName.text = displayName
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeaponSkinViewHolder {
        val binding =
            WeaponSkinItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WeaponSkinViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WeaponSkinViewHolder, position: Int) {
        val weaponSkin = getItem(position)
        weaponSkin.displayIcon?.let {
            weaponSkin.displayName?.let {
                val favoriteWord = holder.itemView.context.getString(R.string.favorite_key)
                val standardWord = holder.itemView.context.getString(R.string.standard_key)
                if (!(weaponSkin.displayName.contains(favoriteWord, true))
                    && !(weaponSkin.displayName.contains(standardWord, true))
                ) {
                    holder.bindItem(weaponSkin)
                }
            }
        }
    }
}