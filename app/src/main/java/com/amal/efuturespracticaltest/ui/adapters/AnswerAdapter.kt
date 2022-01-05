package com.amal.efuturespracticaltest.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.amal.efuturespracticaltest.data.model.Answers
import com.amal.efuturespracticaltest.databinding.ScenarioItemBinding
import com.amal.efuturespracticaltest.ui.interfaces.AnswerRecyclerViewOnClickListener


class AnswerAdapter(private val listener: AnswerRecyclerViewOnClickListener) :
    RecyclerView.Adapter<MainViewHolder>() {

    var answers = mutableListOf<Answers>()

    fun setAnswerList(mAnswers: List<Answers>) {
        this.answers = mAnswers.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ScenarioItemBinding.inflate(inflater, parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val answer = answers[position]
        holder.binding.name.text = answer.text

        holder.binding.root.setOnClickListener {
            listener.onAnswerRecyclerViewItemClick(answer)
        }
    }

    override fun getItemCount(): Int {
        return answers.size
    }
}

