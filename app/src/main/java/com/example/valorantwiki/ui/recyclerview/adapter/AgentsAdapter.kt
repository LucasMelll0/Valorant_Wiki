package com.example.valorantwiki.ui.recyclerview.adapter

import android.content.Context
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.valorantwiki.databinding.AgentItemBinding
import com.example.valorantwiki.model.Agent
import com.example.valorantwiki.ui.activities.AGENT_UUID
import com.example.valorantwiki.ui.activities.AgentActivity
import com.example.valorantwiki.ui.activities.extensions.formatStrToColorStr
import com.example.valorantwiki.ui.activities.extensions.goTo

class AgentsAdapter(
    val context: Context,
) : ListAdapter<Agent, AgentsAdapter.AgentsViewHolder>(differCallBack) {

    companion object {
        private val differCallBack = object : DiffUtil.ItemCallback<Agent>() {
            override fun areItemsTheSame(oldItem: Agent, newItem: Agent): Boolean {
                return oldItem.uuid == newItem.uuid
            }

            override fun areContentsTheSame(oldItem: Agent, newItem: Agent): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class AgentsViewHolder(private val binding: AgentItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindItem(agent: Agent) {
            binding.apply {
                val backgroundColor = agent.backgrondColor.formatStrToColorStr()
                cardviewAgentItem.setBackgroundColor(Color.parseColor("#$backgroundColor"))
                imageviewAgentItem.load(agent.image)
                textviewAgentItemName.text = agent.name
                textviewAgentItemClass.text = agent.role.displayName
            }
            setsUpClick(agent.uuid)
        }


        private fun setsUpClick(uuid: String) {
            itemView.setOnClickListener {
                context.goTo(AgentActivity::class.java){
                    flags = FLAG_ACTIVITY_NEW_TASK
                    putExtra(AGENT_UUID, uuid)
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AgentsViewHolder {
        val binding = AgentItemBinding.inflate(LayoutInflater.from(context), parent, false)

        return AgentsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AgentsViewHolder, position: Int) {
        holder.bindItem(getItem(position))
    }
}