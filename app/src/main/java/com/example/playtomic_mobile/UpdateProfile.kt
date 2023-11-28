package com.example.playtomic_mobile

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class UpdateProfile:Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val extras = intent.extras
        super.onCreate(savedInstanceState)
        setContentView(R.layout.editprofile)

        val LoginID = "70rvGuMShC9KAPgToNOf"
        val ProfileData = Person("null", "null", extras?.getString("Address").toString(), "null", "null", "null", false);

        //fields
        val FirstName = findViewById<View>(R.id.FirstNameInput) as TextView
        FirstName.text = extras?.getString("FirstName");
        val LastName = findViewById<View>(R.id.LastNameInput) as TextView
        LastName.text = extras?.getString("LastName");
        val Court = findViewById<View>(R.id.CourtInput) as TextView
        Court.text = extras?.getString("CourtPosition");
        val Match = findViewById<View>(R.id.MatchInput) as TextView
        Match.text = extras?.getString("MatchType");
        val Play = findViewById<View>(R.id.PlayTimeInput) as TextView
        Play.text = extras?.getString("PlayTime");

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
            startActivity(intent)
        }


        update.setOnClickListener {

                ProfileData.FirstName = FirstName.text.toString();
                ProfileData.LastName = LastName.text.toString();
                ProfileData.CourtPosition = Court.text.toString();
                ProfileData.MatchType = Match.text.toString();
                ProfileData.PreferedPlayTime = Play.text.toString();
                ProfileData.IsRightHanded = IsRightHanded.isChecked;

               db.collection("Persons").document(LoginID).set(ProfileData);

            val intent = Intent(applicationContext, Profile::class.java)
            startActivity(intent)

        }

    }
}