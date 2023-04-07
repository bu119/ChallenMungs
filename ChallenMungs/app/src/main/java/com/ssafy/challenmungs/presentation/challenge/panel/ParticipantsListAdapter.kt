package com.ssafy.challenmungs.presentation.challenge.panel

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.challenmungs.R
import com.ssafy.challenmungs.databinding.ItemParticipantsBinding
import com.ssafy.challenmungs.domain.entity.challenge.Participant

class ParticipantsListAdapter :
    ListAdapter<Participant, ParticipantsListAdapter.ParticipantsViewHolder>(diffUtil) {

    lateinit var binding: ItemParticipantsBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParticipantsViewHolder {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.item_participants, parent, false
        )
        return ParticipantsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ParticipantsViewHolder, position: Int) {
        holder.onBind(currentList[position])
    }

    override fun submitList(list: MutableList<Participant>?) {
        super.submitList(list)
    }

    inner class ParticipantsViewHolder(private val binding: ItemParticipantsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(participant: Participant) {
            binding.participant = participant
        }
    }

    companion object {
        var diffUtil = object : DiffUtil.ItemCallback<Participant>() {
            override fun areItemsTheSame(oldItem: Participant, newItem: Participant): Boolean =
                oldItem.name == newItem.name

            override fun areContentsTheSame(oldItem: Participant, newItem: Participant): Boolean =
                oldItem == newItem
        }
    }
}

