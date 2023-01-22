package com.example.scoutv1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.scoutv1.databinding.ActivitySignInBinding
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignInActivity : AppCompatActivity() {

    //private lateinit var binding: ActivitySignInBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        val signUpTextView = findViewById<TextView>(R.id.textView)
        val resetPassTextView = findViewById<TextView>(R.id.resetPass)
        val emailField = findViewById<EditText>(R.id.emailEt)
        val passField = findViewById<EditText>(R.id.passET)
        val signInButton = findViewById<Button>(R.id.button)

        firebaseAuth = FirebaseAuth.getInstance()

        signUpTextView.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        resetPassTextView.setOnClickListener{
            if (emailField.text.toString().isNotEmpty()) {
                firebaseAuth!!.sendPasswordResetEmail(emailEt.text.toString())
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "Password reset email sent!", Toast.LENGTH_SHORT)
                                .show()
                        } else {
                            Toast.makeText(this, "Email not existed", Toast.LENGTH_SHORT).show()
                        }
                    }
            } else {
                Toast.makeText(this, "Enter email!", Toast.LENGTH_SHORT).show()
            }
        }

        signInButton.setOnClickListener {
            val email = emailField.text.toString()
            val pass = passField.text.toString()

            if (email.isNotEmpty() && pass.isNotEmpty()) {

                firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
                    if (it.isSuccessful) {
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()

                    }
                }
            } else {
                Toast.makeText(this, "Empty fields are not allowed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onStart() {
        super.onStart()

        if(firebaseAuth.currentUser != null){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}