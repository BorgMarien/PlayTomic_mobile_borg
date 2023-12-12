package com.example.playtomic_mobile

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.location.Address
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.Spinner
import android.widget.TextView
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class UpdateProfile:Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val extras = intent.extras
        super.onCreate(savedInstanceState)
        setContentView(R.layout.editprofile)
        val LoginID = extras?.getString("ID").toString()


        val ProfileData = Person("null", "null", extras?.getString("Address").toString(), "null", "null", "null", "null",false,extras?.getString("Password").toString(),extras?.getString("image").toString());

        //fields
        val FirstName = findViewById<View>(R.id.FirstNameInput) as TextView
        FirstName.text = extras?.getString("FirstName");

        val address = findViewById<View>(R.id.addressInput) as TextView
        address.text = extras?.getString("homePlayAddress");
        val LastName = findViewById<View>(R.id.LastNameInput) as TextView
        LastName.text = extras?.getString("LastName");

        val Court = findViewById<View>(R.id.courtinput) as Spinner
        val adapter = ArrayAdapter.createFromResource(this,R.array.Positions, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        Court.adapter = adapter;
        Court.setSelection(adapter.getPosition(extras?.getString("CourtPosition")));



        val Match = findViewById<View>(R.id.MatchInput) as Spinner
        val adapter2 = ArrayAdapter.createFromResource(this,R.array.Matchtypes, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        Match.adapter = adapter2;
        Match.setSelection(adapter2.getPosition(extras?.getString("MatchType")));

        val Play = findViewById<View>(R.id.PlayTimeInput) as Spinner
        val adapter3 = ArrayAdapter.createFromResource(this,R.array.playtime, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        Play.adapter = adapter3;
        Play.setSelection(adapter3.getPosition(extras?.getString("PlayTime")));

        val IsRightHanded = findViewById<View>(R.id.isRightHandedInput) as CheckBox
        if (extras?.getBoolean("IsRightHanded") == true) {
            IsRightHanded.setChecked(true);
        } else {
            IsRightHanded.setChecked(false);
        }


        //firebase
        val db = Firebase.firestore

        //buttons
        val update = findViewById<View>(R.id.update) as Button
        val cancel = findViewById<View>(R.id.cancel) as Button


        //goback
        cancel.setOnClickListener {
            val intent = Intent(applicationContext, Profile::class.java)
            intent.putExtra("ID", extras?.getString("ID"))
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
                ProfileData.Password = ProfileData.Password;
                ProfileData.Image = ProfileData.Image;
               db.collection("Persons").document(LoginID).set(ProfileData);
            val intent = Intent(applicationContext, Profile::class.java)
            intent.putExtra("ID", extras?.getString("ID"))
            startActivity(intent)

        }

    }
}