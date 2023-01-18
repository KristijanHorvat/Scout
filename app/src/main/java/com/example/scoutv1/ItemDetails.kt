package com.example.scoutv1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class ItemDetails : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_details)

        val bundle : Bundle?= intent.extras
        val image = bundle!!.getString("image")
        val name = bundle.getString("name")
        val price = bundle.getString("price")
        val description = bundle.getString("description")
        val phoneNumber = bundle.getString("phoneNumber")


    }
}