package com.example.scoutv1

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_item.*

class ItemFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val bundle = arguments
        val image = bundle!!.getString("image")
        val name = bundle!!.getString("name")
        val price = bundle!!.getString("price")

        val imageItemView = view?.findViewById<ImageView>(R.id.itemImageView)
        val itemNameView = view?.findViewById<TextView>(R.id.nameView)
        val itemPriceView = view?.findViewById<TextView>(R.id.priceView)

        if (itemNameView != null) {
            itemNameView.setText(name)
        }
        if (itemPriceView != null) {
            itemPriceView.setText(price+" €")
        }
        if (itemImageView != null) {
            //itemImageView.setText(name)
        }

    }
}