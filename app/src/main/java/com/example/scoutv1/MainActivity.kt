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
        val buttonBuilding = findViewById<Button>(R.id.button2)
        val buttonPhones = findViewById<Button>(R.id.button3)
        val buttonComputer = findViewById<Button>(R.id.button4)
        val buttonInstruments = findViewById<Button>(R.id.button5)
        val buttonPets = findViewById<Button>(R.id.button6)
        val buttonOther = findViewById<Button>(R.id.button7)

        buttonClick.setOnClickListener {
            val intent = Intent(this, addItemActivity::class.java)
            startActivity(intent)
        }
        buttonVehicle.setOnClickListener {
            val intent = Intent(this, ListItems::class.java)
            intent.putExtra("category", "Vehicle")
            startActivity(intent)
        }

        buttonBuilding.setOnClickListener {
            val intent = Intent(this, ListItems::class.java)
            intent.putExtra("category", "Building")
            startActivity(intent)
        }

        buttonPhones.setOnClickListener {
            val intent = Intent(this, ListItems::class.java)
            intent.putExtra("category", "Phone")
            startActivity(intent)
        }

        buttonComputer.setOnClickListener {
            val intent = Intent(this, ListItems::class.java)
            intent.putExtra("category", "Computer")
            startActivity(intent)
        }

        buttonInstruments.setOnClickListener {
            val intent = Intent(this, ListItems::class.java)
            intent.putExtra("category", "Instrument")
            startActivity(intent)
        }

        buttonPets.setOnClickListener {
            val intent = Intent(this, ListItems::class.java)
            intent.putExtra("category", "Pet")
            startActivity(intent)
        }

        buttonOther.setOnClickListener {
            val intent = Intent(this, ListItems::class.java)
            intent.putExtra("category", "Other")
            startActivity(intent)
        }
    }
}