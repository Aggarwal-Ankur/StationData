package com.aggarwalankur.stationdata.view.stationlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aggarwalankur.stationdata.databinding.StationDataItemBinding
import com.aggarwalankur.stationdata.network.dto.Departure

class DeparturesAdapter (private val viewModel: StationsViewModel) :
    ListAdapter<Departure, DeparturesAdapter.ViewHolder>(DepartureDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)

        holder.bind(viewModel, item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(private val binding: StationDataItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(viewModel: StationsViewModel, item: Departure) {

            binding.viewmodel = viewModel
            binding.item = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = StationDataItemBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }
}

class DepartureDiffCallback : DiffUtil.ItemCallback<Departure>() {
    override fun areItemsTheSame(oldItem: Departure, newItem: Departure): Boolean {
        return oldItem.direction == newItem.direction
    }

    override fun areContentsTheSame(oldItem: Departure, newItem: Departure): Boolean {
        return oldItem == newItem
    }
}