package com.example.preppilot.ui

import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ExplanationAdapter :
    RecyclerView.Adapter<ExplanationAdapter.VH>() {

    private val items = mutableListOf<String>()

    fun submit(data: List<String>) {
        items.clear()
        items.addAll(data)
        notifyDataSetChanged()
    }

    class VH(val tv: TextView) : RecyclerView.ViewHolder(tv)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val tv = TextView(parent.context)
        tv.setPadding(16, 16, 16, 16)
        return VH(tv)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.tv.text = "• ${items[position]}"
    }

    override fun getItemCount(): Int = items.size
}
