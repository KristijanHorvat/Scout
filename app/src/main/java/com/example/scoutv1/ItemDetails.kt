package com.example.scoutv1

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class ItemDetails : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_details)

        val bundle : Bundle?= intent.extras
        val image = bundle!!.getString("image")
        val name = bundle.getString("name")
        val price = bundle.getString("price")
        val description = bundle.getString("description")
        val phoneNumber = bundle.getString("phoneNumber")

        val itemFragment = ItemFragment()
        val detailsFragment = DetailsFragment()
        val contactFragment = ContactFragment()

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        replaceFragment(itemFragment)

        bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.ic_home->replaceFragment(itemFragment)
                R.id.ic_details->replaceFragment(detailsFragment)
                R.id.ic_phone->replaceFragment(contactFragment)
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment){
        if(fragment != null){
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frame_layout, fragment)
            transaction.commit()
        }
    }
}