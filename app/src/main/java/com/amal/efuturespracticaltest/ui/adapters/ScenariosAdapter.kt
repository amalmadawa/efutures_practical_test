package com.amal.efuturespracticaltest.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.amal.efuturespracticaltest.data.model.Scenarios
import com.amal.efuturespracticaltest.databinding.ScenarioItemBinding
import com.amal.efuturespracticaltest.ui.interfaces.ScenariosRecyclerViewOnClickListener


class ScenariosAdapter(private val listener: ScenariosRecyclerViewOnClickListener) : RecyclerView.Adapter<MainViewHolder>() {

    var scenarios = mutableListOf<Scenarios>()

    fun setMovieList(movies: List<Scenarios>) {
        this.scenarios = movies.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ScenarioItemBinding.inflate(inflater, parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val scenario = scenarios[position]
        holder.binding.name.text = scenario.text

        holder.binding.root.setOnClickListener {
            listener.onScenariosRecyclerViewItemClick(scenario)
        }
    }

    override fun getItemCount(): Int {
        return scenarios.size
    }
}

class MainViewHolder(val binding: ScenarioItemBinding) : RecyclerView.ViewHolder(binding.root) {}