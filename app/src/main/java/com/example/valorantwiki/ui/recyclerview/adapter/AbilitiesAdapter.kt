package com.example.valorantwiki.ui.recyclerview.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.valorantwiki.databinding.AbilityItemBinding
import com.example.valorantwiki.webclient.webClientModel.Ability

class AbilitiesAdapter(
    private val context: Context,
    abilityList: List<Ability> = emptyList(),
    val backgroundColor: String
) : RecyclerView.Adapter<AbilitiesAdapter.AbilityViewHolder>() {

    private val dataSet: MutableList<Ability> = abilityList.toMutableList()

    inner class AbilityViewHolder(private val binding: AbilityItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindItem(ability: Ability){
            binding.apply {
                cardviewAbilityItem.setCardBackgroundColor(Color.parseColor("#${backgroundColor}"))
                imageviewAbilityIcon.load(ability.displayIcon)
                textviewAbilityName.text = ability.displayName
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AbilityViewHolder {
        val binding = AbilityItemBinding.inflate(LayoutInflater.from(context), parent, false)

        return AbilityViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AbilityViewHolder, position: Int) {
        holder.bindItem(dataSet[position])
    }

    override fun getItemCount(): Int = dataSet.size
}