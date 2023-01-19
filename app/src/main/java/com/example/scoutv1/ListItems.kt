package com.example.scoutv1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ListItems : AppCompatActivity() {

    private val db = Firebase.firestore
    private lateinit var recyclerAdapter: RecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        var recyclerView = findViewById<RecyclerView>(R.id.itemsList)
        var category = intent.getStringExtra("category");
        var flagSearch = intent.getStringExtra("flag");
        var searchItem = intent.getStringExtra("name");


        if (flagSearch == "1") {
            db.collection("search").whereEqualTo("name", searchItem)
                .get()
                .addOnSuccessListener { result ->
                    val itemsList = ArrayList<Item>()
                    for (data in result.documents) {
                        val item = data.toObject(Item::class.java)
                        Log.w("ListItems", "Error getting documents.")
                        if (item != null) {
                            item.itemId = data.id
                            itemsList.add(item)
                        }
                    }
                    recyclerAdapter = RecyclerAdapter(itemsList)
                    recyclerView.apply {
                        layoutManager = LinearLayoutManager(this@ListItems)
                        adapter = recyclerAdapter
                        recyclerAdapter.setOnItemClickListener(object :
                            RecyclerAdapter.onItemClickListener {
                            override fun onItemClick(position: Int) {
                                //Toast.makeText(this@ListItems, "you clicked", Toast.LENGTH_SHORT).show()
                                val intent = Intent(this@ListItems, ItemDetails::class.java)
                                intent.putExtra("image", itemsList[position].imageLink)
                                if (itemsList[position].imageLink != null) {
                                    itemsList[position].imageLink?.let {
                                        Log.w(
                                            "image link before",
                                            it
                                        )
                                    }
                                }
                                intent.putExtra("name", itemsList[position].name)
                                intent.putExtra("price", itemsList[position].itemPrice)
                                intent.putExtra("description", itemsList[position].description)
                                intent.putExtra("phoneNumber", itemsList[position].phoneNumber)
                                startActivity(intent)
                            }

                        })

                    }
                }
                .addOnFailureListener { exception ->
                    Log.w(
                        "ListItems", "Error getting documents.",
                        exception
                    )
                }
        }else {

            db.collection("search").whereEqualTo("itemCategory", category)
                .get()
                .addOnSuccessListener { result ->
                    val itemsList = ArrayList<Item>()
                    for (data in result.documents) {
                        val item = data.toObject(Item::class.java)
                        Log.w("ListItems", "Error getting documents.")
                        if (item != null) {
                            item.itemId = data.id
                            itemsList.add(item)
                        }
                    }
                    recyclerAdapter = RecyclerAdapter(itemsList)
                    recyclerView.apply {
                        layoutManager = LinearLayoutManager(this@ListItems)
                        adapter = recyclerAdapter
                        recyclerAdapter.setOnItemClickListener(object :
                            RecyclerAdapter.onItemClickListener {
                            override fun onItemClick(position: Int) {
                                //Toast.makeText(this@ListItems, "you clicked", Toast.LENGTH_SHORT).show()
                                val intent = Intent(this@ListItems, ItemDetails::class.java)
                                intent.putExtra("image", itemsList[position].imageLink)
                                if (itemsList[position].imageLink != null) {
                                    itemsList[position].imageLink?.let {
                                        Log.w(
                                            "image link before",
                                            it
                                        )
                                    }
                                }
                                intent.putExtra("name", itemsList[position].name)
                                intent.putExtra("price", itemsList[position].itemPrice)
                                intent.putExtra("description", itemsList[position].description)
                                intent.putExtra("phoneNumber", itemsList[position].phoneNumber)
                                startActivity(intent)
                            }

                        })

                    }
                }
                .addOnFailureListener { exception ->
                    Log.w(
                        "ListItems", "Error getting documents.",
                        exception
                    )
                }

    }
    }


}