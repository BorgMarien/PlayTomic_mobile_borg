package com.example.playtomic_mobile

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
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
        val ProfileData:Person ;

        //values
        val name = findViewById<View>(R.id.name) as TextView
        val location = findViewById<View>(R.id.location) as TextView
        val hand = findViewById<View>(R.id.hand) as TextView
        val courtPosition = findViewById<View>(R.id.court) as TextView
        val matchType = findViewById<View>(R.id.match) as TextView
        val PreferedPlayTime = findViewById<View>(R.id.playtime) as TextView

        //firebase
        val db = Firebase.firestore

        //getdata
        db.collection("Persons")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    //TODO change this so it doenst just take the first one

                    Log.d(TAG, "${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }




        //nav inputs
        val profiel = findViewById<View>(R.id.nav_profiel) as TextView
        val velden = findViewById<View>(R.id.nav_veld) as TextView
        val matchen = findViewById<View>(R.id.nav_match) as TextView
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