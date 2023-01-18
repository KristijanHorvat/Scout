package com.example.scoutv1

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment


class ContactFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_contact, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val bundle = arguments
        val number = bundle!!.getString("phoneNumber")

        val numberView = view?.findViewById<TextView>(R.id.phoneNumberView)
        val buttonCall = view?.findViewById<Button>(R.id.callButton)

        if (numberView != null) {
            numberView.setText(number)
        }

        if (buttonCall != null) {
            buttonCall.setOnClickListener {
                val callIntent = Intent(Intent.ACTION_DIAL)
                callIntent.data = Uri.parse("tel:"+number)
                startActivity(callIntent)
            }
        }
    }
}