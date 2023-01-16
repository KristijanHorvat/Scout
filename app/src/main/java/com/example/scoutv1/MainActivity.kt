package com.example.scoutv1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonClick = findViewById<Button>(R.id.button8)
        val buttonVehicle = findViewById<Button>(R.id.button1)

        buttonClick.setOnClickListener {
            val intent = Intent(this, addItemActivity::class.java)
            startActivity(intent)
        }
        buttonVehicle.setOnClickListener {
            val intent = Intent(this, ListItems::class.java)
            startActivity(intent)
        }
    }
}