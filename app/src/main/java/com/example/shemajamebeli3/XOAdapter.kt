package com.example.shemajamebeli3

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shemajamebeli3.databinding.ItemSquareBinding

class XOAdapter (private val Click: (Item) -> Unit) :
    RecyclerView.Adapter<XOAdapter.XOViewHolder>() {
    private var list: List<Item> = emptyList()

    inner class XOViewHolder(private val binding: ItemSquareBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(model: Item, Click: (Item) -> Unit) {
            model.imgResource?.let {
                binding.itemView.setImageResource(model.imgResource!!)
            }
            binding.itemView.isClickable = model.isClickable
            binding.itemView.setBackgroundResource(model.imgBg)
            itemView.setOnClickListener { Click(model) }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): XOViewHolder {
        val binding = ItemSquareBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return XOViewHolder(binding)
    }

    override fun onBindViewHolder(holder: XOViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item, Click)
    }

    override fun getItemCount(): Int = list.size

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<Item>) {
        this.list = list
        notifyDataSetChanged()
    }
}
