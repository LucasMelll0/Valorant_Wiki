package com.example.valorantwiki.ui.recyclerview.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.valorantwiki.databinding.AgentItemBinding
import com.example.valorantwiki.model.Agent

class AgentsAdapter(
    val context: Context,
    agentsList: List<Agent> = emptyList()
) : RecyclerView.Adapter<AgentsAdapter.AgentsViewHolder>() {

    private val dataSet : MutableList<Agent> = agentsList.toMutableList()


    class AgentsViewHolder(val binding: AgentItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindItem(agent: Agent) {
            binding.apply {
                imageviewAgentItem.load(agent.fullPortrait)
                textviewAgentItemName.text = agent.displayName
                textviewAgentItemClass.text = agent.role.displayName

            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AgentsViewHolder {
        val binding = AgentItemBinding.inflate(LayoutInflater.from(context), parent, false)

        return AgentsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AgentsViewHolder, position: Int) {
        holder.bindItem(dataSet.get(position))
    }

    override fun getItemCount(): Int = dataSet.size

    @SuppressLint("NotifyDataSetChanged")
    fun addAll(agents: List<Agent>){
        dataSet.addAll(agents)
        notifyDataSetChanged()
    }
}