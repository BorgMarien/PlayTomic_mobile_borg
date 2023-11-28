package com.example.playtomic_mobile

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigator
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class CreateMatch: Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val extras = intent.extras
        super.onCreate(savedInstanceState)
        setContentView(R.layout.creatematch_layout)


        //nav
        val backbutton = findViewById<View>(R.id.back) as TextView

        //fields
        val fieldName = findViewById<View>(R.id.fieldname) as TextView
        fieldName.text= extras?.getString("Name")
        val fieldAddress = findViewById<View>(R.id.fieldadress) as TextView
        fieldAddress.text= extras?.getString("address")

        val dateinput = findViewById<View>(R.id.dateInput) as TextView
        val timeinput = findViewById<View>(R.id.TimeInput) as TextView
        val numberofplayersinput = findViewById<View>(R.id.PlayerInput) as TextView
        val CompetitiveMatch = findViewById<View>(R.id.competitiveInput) as CheckBox
        val createbutton = findViewById<View>(R.id.CreateButton) as Button

        val db = Firebase.firestore
        val field= Field(extras?.getString("id").toString(), extras?.getString("Name").toString(),extras?.getString("address").toString())

        createbutton.setOnClickListener{

          val Match = Match(dateinput.text.toString(),timeinput.text.toString(),field,CompetitiveMatch.isChecked,numberofplayersinput.text.toString().toInt(),
                ArrayList<Person>()
            );
          db.collection("Match").add(Match)

          val intent = Intent(applicationContext, Velden::class.java)
          startActivity(intent)
        }

        backbutton.setOnClickListener{
            val intent = Intent(applicationContext, Velden::class.java)
            startActivity(intent)
        }

    }


}