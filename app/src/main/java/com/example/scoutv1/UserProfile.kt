package com.example.scoutv1

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

class UserProfile : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        firebaseAuth = FirebaseAuth.getInstance()

        val logOutButton = findViewById<Button>(R.id.logOutButton)
        val resetPassBtn = findViewById<Button>(R.id.resetPassBtn)

        logOutButton.setOnClickListener {
            firebaseAuth.signOut()
                val intent= Intent(this, SignInActivity::class.java)
                startActivity(intent)
                finish()
        }

        resetPassBtn.setOnClickListener {
            firebaseAuth!!.sendPasswordResetEmail(firebaseAuth.currentUser?.email.toString())
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Password reset email sent!", Toast.LENGTH_SHORT).show()
                } else {
                        Toast.makeText(this, "Email not existed", Toast.LENGTH_SHORT).show()
                }
                }
        }
    }
}