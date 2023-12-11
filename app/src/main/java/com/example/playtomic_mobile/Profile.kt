package com.example.playtomic_mobile

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.remember
import com.beust.klaxon.JsonArray
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.storage
import io.grpc.Context
import java.io.File
import java.lang.StringBuilder

class Profile : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile_layout)
        val extras = intent.extras
        //persons
        val ProfileData = Person("null","null","null","null","null","null","null",false,"");
        val LoginID= extras?.getString("ID").toString()

        //fields
        val name = findViewById<View>(R.id.name) as TextView
        val location = findViewById<View>(R.id.location) as TextView
        val hand = findViewById<View>(R.id.hand) as TextView
        val courtPosition = findViewById<View>(R.id.court) as TextView
        val matchType = findViewById<View>(R.id.match) as TextView
        val PreferedPlayTime = findViewById<View>(R.id.playtime) as TextView
        val image = findViewById<View>(R.id.porfileImg) as ImageView
        val logout = findViewById<View>(R.id.logout) as TextView

        //firebase
        val db = Firebase.firestore

        //set all values
        fun setProfielData(){
            name.text= ProfileData.FirstName + " " + ProfileData.LastName;
            location.text = ProfileData.HomePlayAddress;
            courtPosition.text= ProfileData.CourtPosition;
            matchType.text= ProfileData.MatchType;
            PreferedPlayTime.text = ProfileData.PreferedPlayTime;
            if(ProfileData.IsRightHanded){
                hand.text = "Right handed"
            }else{
                hand.text = "Left handed"
            }
        }


        //getdata by id
        val docref = db.collection("Persons").document(LoginID)
        docref.get()
            .addOnSuccessListener { document ->

                if (document != null) {
                    ProfileData.FirstName= document.data?.get("firstName").toString();
                    ProfileData.LastName= document.data?.get("lastName").toString();
                    ProfileData.HomePlayAddress= document.data?.get("homePlayAddress").toString();
                    ProfileData.MatchType= document.data?.get("matchType").toString();
                    ProfileData.PreferedPlayTime= document.data?.get("preferedPlayTime").toString();
                    ProfileData.CourtPosition= document.data?.get("courtPosition").toString();
                    ProfileData.IsRightHanded=document.data?.get("isRightHanded").toString().toBoolean();
                    ProfileData.Password=document.data?.get("passWord").toString();

                    setProfielData();

                }
            }




        //nav inputs
        val profiel = findViewById<View>(R.id.nav_profiel) as TextView
        val velden = findViewById<View>(R.id.nav_veld) as TextView
        val matchen = findViewById<View>(R.id.nav_match) as TextView
        val updateButton = findViewById<View>(R.id.button) as Button



        updateButton.setOnClickListener{
            val intent = Intent(applicationContext, UpdateProfile::class.java)
            intent.putExtra("FirstName", ProfileData.FirstName)
            intent.putExtra("homePlayAddress", ProfileData.HomePlayAddress)
            intent.putExtra("LastName", ProfileData.LastName)
            intent.putExtra("CourtPosition", ProfileData.CourtPosition)
            intent.putExtra("MatchType", ProfileData.MatchType)
            intent.putExtra("PlayTime", ProfileData.PreferedPlayTime)
            intent.putExtra("IsRightHanded", ProfileData.IsRightHanded)
            intent.putExtra("Address", ProfileData.HomePlayAddress)
            intent.putExtra("PassWord", ProfileData.Password)
            intent.putExtra("ID", extras?.getString("ID"))
            startActivity(intent)
        }

        profiel.setOnClickListener{
            val intent = Intent(applicationContext, Profile::class.java)

            intent.putExtra("ID", extras?.getString("ID"))
            startActivity(intent)
        }
        logout.setOnClickListener{
            val intent = Intent(applicationContext, login::class.java)
            intent.putExtra("ID", extras?.getString("ID"))
            startActivity(intent)
        }

        velden.setOnClickListener{
            val intent = Intent(applicationContext, Velden::class.java)
            intent.putExtra("ID", extras?.getString("ID"))
            startActivity(intent)
        }

        matchen.setOnClickListener{
            val intent = Intent(applicationContext, Matches::class.java)
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
            Log.d(TAG, "${data?.data.toString()}")
            val image = findViewById<View>(R.id.porfileImg) as ImageView
            image.setImageURI(data?.data)
       }

}