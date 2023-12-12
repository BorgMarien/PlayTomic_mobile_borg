package com.example.playtomic_mobile

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import java.io.File
import java.util.UUID

class createUser : Activity() {
    val ProfileData = Person("null","null","null","null","null","null","null",false,"","");

    override fun onCreate(savedInstanceState: Bundle?) {
        val extras = intent.extras
        super.onCreate(savedInstanceState)
        setContentView(R.layout.createperson)

        //fields
        val FirstName = findViewById<View>(R.id.firstNameInput4) as TextView
        val address = findViewById<View>(R.id.addressInput4) as TextView
        val LastName = findViewById<View>(R.id.lastNameInput3) as TextView
        val password = findViewById<View>(R.id.editTextTextPassword) as TextView
        val image = findViewById<View>(R.id.imageView2) as ImageView
        val Court = findViewById<View>(R.id.courtinput4) as Spinner
        val adapter = ArrayAdapter.createFromResource(this,R.array.Positions, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        Court.adapter = adapter;

        val Match = findViewById<View>(R.id.matchInput3) as Spinner
        val adapter2 = ArrayAdapter.createFromResource(this,R.array.Matchtypes, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        Match.adapter = adapter2;

        val Play = findViewById<View>(R.id.playTimeInput3) as Spinner
        val adapter3 = ArrayAdapter.createFromResource(this,R.array.playtime, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        Play.adapter = adapter3;

        val IsRightHanded = findViewById<View>(R.id.isRightHandedInput4) as CheckBox

        //firebase
        val db = Firebase.firestore

        //buttons
        val update = findViewById<View>(R.id.update2) as Button
        val cancel = findViewById<View>(R.id.update3) as Button

        //goback
        cancel.setOnClickListener {
            val intent = Intent(applicationContext, login::class.java)
            startActivity(intent)
        }

        update.setOnClickListener {
            ProfileData.FirstName = FirstName.text.toString();
            ProfileData.HomePlayAddress = address.text.toString();
            ProfileData.LastName = LastName.text.toString();
            ProfileData.CourtPosition = Court.selectedItem.toString();
            ProfileData.MatchType = Match.selectedItem.toString();
            ProfileData.PreferedPlayTime = Play.selectedItem.toString();
            ProfileData.IsRightHanded = IsRightHanded.isChecked;
            ProfileData.Password = password.text.toString();

            db.collection("Persons").add(ProfileData);

            val intent = Intent(applicationContext, login::class.java)
            intent.putExtra("ID", extras?.getString("ID"))
            startActivity(intent)

        }

        image.setOnClickListener{
            chooseImageGallery()
        }



    }
    private fun chooseImageGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_CHOOSE)
    }

    companion object {
        private val IMAGE_CHOOSE = 1000;
        private val PERMISSION_CODE = 1001;
    }
    private val REQUEST_CODE = 13
    private lateinit var filePhoto: File
    private val FILE_NAME = "photo.jpg"

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode){
            PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    chooseImageGallery()
                }else{
                    Toast.makeText(this,"Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.d(ContentValues.TAG, "Found data  ${data?.data}")
       val image = findViewById<View>(R.id.imageView2) as ImageView
        image.setImageURI(data?.data)

        data?.data?.let { uploadImage(it) };


    }


    private fun uploadImage(imageUri: Uri) {
        val db = Firebase.firestore
        // Get a reference to the Firebase Storage
        val storageRef = FirebaseStorage.getInstance().reference

        // Create a reference to the image file you want to upload
        val imageRef = storageRef.child("images/" + UUID.randomUUID().toString())

        // Upload the file to Firebase Storage
        imageRef.putFile(imageUri)
            .addOnSuccessListener { taskSnapshot: UploadTask.TaskSnapshot? ->
                // Image uploaded successfully
                // Get the download URL of the uploaded image
                imageRef.downloadUrl
                    .addOnSuccessListener { uri: Uri ->
                        //firebase
                        // Save the download URL to the Realtime Database or perform any other desired action
                        ProfileData.Image = uri.toString();
                    }
            }
            .addOnFailureListener { exception: Exception ->
                // Handle unsuccessful uploads
                Log.e("TAG", "Error uploading image: " + exception.message)
            }
    }
}
