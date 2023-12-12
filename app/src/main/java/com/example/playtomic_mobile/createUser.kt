package com.example.playtomic_mobile

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.Spinner
import android.widget.TextView
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class createUser : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val extras = intent.extras
        super.onCreate(savedInstanceState)
        setContentView(R.layout.createperson)

        val ProfileData = Person("null", "null", extras?.getString("Address").toString(), "null", "null", "null", "null",false,extras?.getString("Password").toString(),"");

        //fields
        //fields
        val FirstName = findViewById<View>(R.id.firstNameInput4) as TextView
        val address = findViewById<View>(R.id.addressInput4) as TextView
        val LastName = findViewById<View>(R.id.lastNameInput3) as TextView
        val password = findViewById<View>(R.id.editTextTextPassword) as TextView

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


    }
}

