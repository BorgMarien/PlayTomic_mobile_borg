package com.example.playtomic_mobile

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.beust.klaxon.JsonArray
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import java.lang.StringBuilder

class Profile : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile_layout)
        //persons
        val ProfileData = Person("null","null","null","null","null","null","null",false);
        val LoginID= "70rvGuMShC9KAPgToNOf"

        //fields
        val name = findViewById<View>(R.id.name) as TextView
        val location = findViewById<View>(R.id.location) as TextView
        val hand = findViewById<View>(R.id.hand) as TextView
        val courtPosition = findViewById<View>(R.id.court) as TextView
        val matchType = findViewById<View>(R.id.match) as TextView
        val PreferedPlayTime = findViewById<View>(R.id.playtime) as TextView

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
            intent.putExtra("LastName", ProfileData.LastName)
            intent.putExtra("CourtPosition", ProfileData.CourtPosition)
            intent.putExtra("MatchType", ProfileData.MatchType)
            intent.putExtra("PlayTime", ProfileData.PreferedPlayTime)
            intent.putExtra("IsRightHanded", ProfileData.IsRightHanded)
            intent.putExtra("Address", ProfileData.HomePlayAddress)
            startActivity(intent)
        }

        profiel.setOnClickListener{
            val intent = Intent(applicationContext, Profile::class.java)
            startActivity(intent)
        }

        velden.setOnClickListener{
            val intent = Intent(applicationContext, Velden::class.java)
            startActivity(intent)
        }

        matchen.setOnClickListener{
            val intent = Intent(applicationContext, Matches::class.java)
            startActivity(intent)
        }

    }



}