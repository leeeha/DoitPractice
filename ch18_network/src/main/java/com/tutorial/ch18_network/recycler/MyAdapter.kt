package com.tutorial.ch18_network.recycler

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tutorial.ch18_network.R
import com.tutorial.ch18_network.databinding.RecyclerviewItemBinding
import com.tutorial.ch18_network.model.ItemModel

class MyViewHolder(val binding: RecyclerviewItemBinding) : RecyclerView.ViewHolder(binding.root)

class MyAdapter(private val context: Context, private val data: MutableList<ItemModel>?) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemCount(): Int {
        return data?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        MyViewHolder(
            RecyclerviewItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as MyViewHolder).binding

        val model = data!![position]
        binding.itemTitle.text = model.title
        binding.itemDesc.text = model.description
        binding.itemTime.text = "${model.author} at ${model.publishedAt}"

        Glide.with(context)
            .load(model.urlToImage)
            .into(binding.itemImage)
    }
}