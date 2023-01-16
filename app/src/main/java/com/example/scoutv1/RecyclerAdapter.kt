package com.example.scoutv1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class RecyclerAdapter(val items: ArrayList<Item>,val listener: ContentListener):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is ItemViewHolder -> {
                holder.bind(position, items[position], listener)
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ItemViewHolder(val view: View): RecyclerView.ViewHolder(view){
        private val itemImage = view.findViewById<ImageView>(R.id.ItemImage)
        private val itemName = view.findViewById<TextView>(R.id.ItemNameView)
        private val itemDescription = view.findViewById<TextView>(R.id.ItemDescriptionView)

        fun bind(index: Int, item: Item, listener: ContentListener){
            Glide.with(view.context).load(item.imageLink).into(itemImage)
            itemName.setText(item.name)
            itemDescription.setText(item.description)
        }
    }

    interface ContentListener{
        fun onItemButtonClick(index: Int, person: Item)
    }
}