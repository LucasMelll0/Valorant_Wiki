package com.example.valorantwiki.ui.recyclerview.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.valorantwiki.R
import com.example.valorantwiki.databinding.DamageRangeItemBinding
import com.example.valorantwiki.webclient.webClientModel.DamageRange

class DamageRangesAdapter() :
    ListAdapter<DamageRange, DamageRangesAdapter.DamageRangesViewHolder>(differCallBack) {

    companion object {
        private val differCallBack = object : DiffUtil.ItemCallback<DamageRange>() {
            override fun areItemsTheSame(oldItem: DamageRange, newItem: DamageRange): Boolean {
                return oldItem.rangeStartMeters == newItem.rangeStartMeters
            }

            override fun areContentsTheSame(oldItem: DamageRange, newItem: DamageRange): Boolean {
                return oldItem == newItem
            }
        }
    }

    class DamageRangesViewHolder(private val binding: DamageRangeItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindItem(damageRange: DamageRange) {
            val textViewRange = binding.textviewDamageRange
            val textViewHeadDamage = binding.textviewHeadDamage
            val textViewBody = binding.textviewBodyDamage
            val textViewLegs = binding.textviewLegsDamage
            textViewRange.text = binding.root.context.getString(
                R.string.item_damage_range_format,
                damageRange.rangeStartMeters.toString(),
                damageRange.rangeEndMeters.toString()
            )
            textViewHeadDamage.text =
                binding.root.context.getString(
                    R.string.item_damage_range_damage_format,
                    damageRange.headDamage
                )
            textViewBody.text = binding.root.context.getString(
                R.string.item_damage_range_damage_format,
                damageRange.bodyDamage
            )
            textViewLegs.text = binding.root.context.getString(
                R.string.item_damage_range_damage_format,
                damageRange.legDamage
            )
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DamageRangesViewHolder {
        val binding =
            DamageRangeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DamageRangesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DamageRangesViewHolder, position: Int) {
        holder.bindItem(getItem(position))
    }
}