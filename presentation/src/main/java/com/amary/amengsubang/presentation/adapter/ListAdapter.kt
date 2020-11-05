package com.amary.amengsubang.presentation.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.amary.amengsubang.presentation.R
import com.amary.amengsubang.presentation.databinding.ItemHomeBinding
import com.amary.amengsubang.presentation.model.Place
import com.amary.amengsubang.presentation.utils.ListCallback
import com.amary.amengsubang.presentation.utils.inflate

class ListAdapter(private val callback: ListCallback) : RecyclerView.Adapter<ListAdapter.ListHolder>(){

    private val place = arrayListOf<Place>()

    fun addItem(listPlace: ArrayList<Place>){
        place.clear()
        place.addAll(listPlace)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListHolder {
        return ListHolder(parent.inflate(R.layout.item_home), callback)
    }

    override fun onBindViewHolder(holder: ListHolder, position: Int) {
        holder.bind(place[position])
    }

    override fun getItemCount() = place.size

    class ListHolder(private val binding: ItemHomeBinding, callback: ListCallback): RecyclerView.ViewHolder(binding.root) {

        init {
            binding.callback = callback
        }

        fun bind(place: Place) {
            binding.place = place
        }
    }
}