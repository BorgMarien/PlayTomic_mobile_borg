package com.example.playtomic_mobile

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CalendarView
import android.widget.CheckBox
import android.widget.Spinner
import android.widget.TextView
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class CreateMatch: Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val extras = intent.extras
        super.onCreate(savedInstanceState)
        setContentView(R.layout.creatematch)
        val LoginID = "70rvGuMShC9KAPgToNOf"
        val PlayerList = ArrayList<String> ();
        PlayerList.add((LoginID));

        //nav
        val back = findViewById<View>(R.id.backButton) as TextView
        back.setOnClickListener{
            val intent = Intent(applicationContext, Matches::class.java)
            startActivity(intent)
        }

        //fields
        val time = findViewById<View>(R.id.MatchTime) as Spinner
        val adapter = ArrayAdapter.createFromResource(this,R.array.available, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        time.adapter = adapter;

        val matchname =  findViewById<View>(R.id.MatchName) as TextView
        val MatchType =  findViewById<View>(R.id.MatchType) as CheckBox

        val clubs = findViewById<View>(R.id.MatchField) as Spinner
        val adapter2 = ArrayAdapter.createFromResource(this,R.array.clubs, android.R.layout.simple_spinner_item)
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        clubs.adapter = adapter2;

        val date = findViewById<View>(R.id.MatchDate) as CalendarView
        var stringdate = "";
        //get value
        date
            .setOnDateChangeListener(
                CalendarView.OnDateChangeListener { view, year, month, dayOfMonth ->
                    stringdate = (dayOfMonth.toString() + "-"
                            + (month + 1) + "-" + year)

                })



        val db = Firebase.firestore
        val createbutton = findViewById<View>(R.id.CreateMatchButton) as Button
        createbutton.setOnClickListener{
            val Match = Match(matchname.text.toString(),clubs.selectedItem.toString(),stringdate,time.selectedItem.toString(),MatchType.isChecked,PlayerList);
            db.collection("Matches").add(Match)
            val intent = Intent(applicationContext, Matches::class.java)
            startActivity(intent)
        }
    }
}