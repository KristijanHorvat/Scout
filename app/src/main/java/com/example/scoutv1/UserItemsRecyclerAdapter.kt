package com.example.scoutv1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

enum class ItemClickType {
    EDIT,
    REMOVE,
}
class UserItemsRecyclerAdapter(
    val items: ArrayList<Item>,
    val listener: ContentListener

): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            RecyclerView.ViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.user_recycler_item_edit_remove,
                parent, false)
        )
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ItemViewHolder -> {
                holder.bind(position, listener, items[position])
            }
        }
    }
    override fun getItemCount(): Int {
        return items.size
    }
    fun removeItem(index: Int) {
        items.removeAt(index)
        notifyItemRemoved(index)
        notifyItemRangeChanged(index, items.size)
    }
    class ItemViewHolder(val view: View): RecyclerView.ViewHolder(view) {
        private val userItemImage = view.findViewById<ImageView>(R.id.usersItemImage)
        private val itemName = view.findViewById<EditText>(R.id.usersItemName)
        private val itemDescription = view.findViewById<EditText>(R.id.usersItemDescription)
        private val itemPrice = view.findViewById<EditText>(R.id.usersItemPrice)
        private val contactNumber = view.findViewById<EditText>(R.id.usersItemContactNumber)
        private val editBtn = view.findViewById<ImageButton>(R.id.editButton)
        private val deleteBtn = view.findViewById<ImageButton>(R.id.deleteButton)
        private val db = Firebase.storage
        private var imageURL = ""

        fun bind(index: Int, listener: ContentListener, item: Item) {
            val imgPath = db.reference.child("myImages/"+item.imageLink)
            //Log.d("ada", imgPath.toString()) gs:// url
            imgPath.downloadUrl.addOnSuccessListener {Uri->
                imageURL = Uri.toString()
                //Log.d("imageURL in recycler", imageURL) //full https url
                Glide.with(view.context).load(imageURL).into(userItemImage)
            }
            itemName.setText(item.name)
            itemDescription.setText(item.description)
            itemPrice.setText(item.itemPrice)
            contactNumber.setText(item.phoneNumber)

            editBtn.setOnClickListener {
                item.name = itemName.text.toString()
                item.description = itemDescription.text.toString()
                item.itemPrice = itemPrice.text.toString()
                item.phoneNumber = contactNumber.text.toString()
                listener.onItemButtonClick(index, item, ItemClickType.EDIT)
            }
            deleteBtn.setOnClickListener {
                listener.onItemButtonClick(index, item, ItemClickType.REMOVE)
            }
        }
    }
    interface ContentListener {
        fun onItemButtonClick(index: Int, item: Item, clickType: ItemClickType)
    }
}