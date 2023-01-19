package com.example.scoutv1

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class UserProfile : AppCompatActivity(), UserItemsRecyclerAdapter.ContentListener {
    private lateinit var firebaseAuth: FirebaseAuth
    private val db = Firebase.firestore
    private lateinit var recyclerAdapter: UserItemsRecyclerAdapter

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        firebaseAuth = FirebaseAuth.getInstance()

        val logOutButton = findViewById<Button>(R.id.logOutButton)
        val resetPassBtn = findViewById<Button>(R.id.resetPassBtn)
        val emailTextView = findViewById<TextView>(R.id.emailTextView)
        val recyclerViewItems = findViewById<RecyclerView>(R.id.UserItemsView)

        val email = firebaseAuth.currentUser?.email.toString()
        emailTextView.text = email

        logOutButton.setOnClickListener {
            firebaseAuth.signOut()
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
            finish()
        }

        resetPassBtn.setOnClickListener {
            firebaseAuth!!.sendPasswordResetEmail(email)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Password reset email sent!", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        Toast.makeText(this, "Email not existed", Toast.LENGTH_SHORT).show()
                    }
                }
        }

        db.collection("search").whereEqualTo("userMail", email)
            .get()
            .addOnSuccessListener { result ->
                val itemList = ArrayList<Item>()
                for (data in result.documents) {
                    val item = data.toObject(Item::class.java)
                    if (item != null) {
                        item.itemId = data.id
                        itemList.add(item)
                    }
                }
                recyclerAdapter = UserItemsRecyclerAdapter(itemList, this@UserProfile)
                recyclerViewItems.apply {
                    layoutManager = LinearLayoutManager(this@UserProfile)
                    adapter = recyclerAdapter
                }
            }
            .addOnFailureListener { exception ->
                Log.w(
                    "UserProfile", "Error getting documents.",
                    exception
                )
            }
    }

    override fun onItemButtonClick(index: Int, item: Item, clickType: ItemClickType) {
            if (clickType == ItemClickType.EDIT) {
                db.collection("search")
                    .document(item.itemId)
                    .set(item)
            } else if (clickType == ItemClickType.REMOVE) {
                recyclerAdapter.removeItem(index)
                db.collection("search")
                    .document(item.itemId)
                    .delete()
            }
        }
}