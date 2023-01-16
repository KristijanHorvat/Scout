package com.example.scoutv1

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.content.ContextCompat
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_add_item.*
import java.io.IOException
import java.util.*

class addItemActivity : AppCompatActivity() {

    val PICK_IMAGE_REQUEST = 71
    var filePath: Uri? = null
    var firebaseStore: FirebaseStorage? = null
    var storageReference: StorageReference? = null
    lateinit var btn_upload_image: Button
    lateinit var imagePreview: ImageView
    lateinit var itemName: EditText
    lateinit var itemDescription: EditText
    lateinit var itemPhoneNumber: EditText
    var path: String = ""
    private val db = Firebase.firestore
    lateinit var categoriesSpinner: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)


        firebaseStore = FirebaseStorage.getInstance()
        storageReference = FirebaseStorage.getInstance().reference

        btn_upload_image = findViewById(R.id.button9)
        imagePreview = findViewById<ImageView>(R.id.imageView)
        itemName = findViewById(R.id.ItemName)
        itemDescription = findViewById(R.id.ItemDescription)
        itemPhoneNumber = findViewById(R.id.ItemPhoneNUmber)
        categoriesSpinner = findViewById(R.id.itemCategory)

        //Spinner adapter
        ArrayAdapter.createFromResource(
            this,
            R.array.categories_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            categoriesSpinner.adapter = adapter
        }


        imagePreview.setOnClickListener { launchGallery() }
        btn_upload_image.setOnClickListener {
            uploadImage()

            val item = Item(
                itemName.text.toString(),
                path.toString(),
                itemDescription.text.toString(),
                itemPhoneNumber.text.toString()
            )
            var itemSelected = categoriesSpinner.getSelectedItem().toString();
            db.collection(itemSelected)
                .add(item)
                .addOnSuccessListener { documentReference ->
                    Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                    Toast.makeText(this, "Upload success", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "Error adding document", e)
                    Toast.makeText(this, "Error adding item!", Toast.LENGTH_SHORT).show()
                }
        }

    }

    private fun launchGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
    }

    private fun uploadImage() {
        if (filePath != null) {
            path = "myImages/" + UUID.randomUUID().toString()
            val ref = storageReference?.child(path)
            val uploadTask = ref?.putFile(filePath!!)
            Toast.makeText(this, "Upload image success", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Please Upload an Image", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            if (data == null || data.data == null) {
                return
            }
            filePath = data.data
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, filePath)
                imagePreview.setImageBitmap(bitmap)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}


