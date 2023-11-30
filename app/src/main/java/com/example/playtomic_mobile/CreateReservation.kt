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

class CreateReservation: Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val extras = intent.extras
        super.onCreate(savedInstanceState)
        setContentView(R.layout.reservefield)


        //nav
        val backbutton = findViewById<View>(R.id.back) as TextView

        //fields
        val date = findViewById<View>(R.id.dateInput) as CalendarView
        var stringdate = "";

        val time = findViewById<View>(R.id.time) as Spinner
        val adapter = ArrayAdapter.createFromResource(this,R.array.available, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        time.adapter = adapter;

        val CompetitiveMatch = findViewById<View>(R.id.competitiveInput) as CheckBox
        val createbutton = findViewById<View>(R.id.CreateButton) as Button

        val db = Firebase.firestore
        val field= Field(extras?.getString("id").toString(), extras?.getString("Name").toString(),extras?.getString("address").toString())

        //get value
        date
            .setOnDateChangeListener(
                CalendarView.OnDateChangeListener { view, year, month, dayOfMonth ->
                   stringdate = (dayOfMonth.toString() + "-"
                            + (month + 1) + "-" + year)

                })

        createbutton.setOnClickListener{

          val reservation = Reservation(stringdate,time.selectedItem.toString(),field,CompetitiveMatch.isChecked,ArrayList<Person>());
          db.collection("Reservation").add(reservation)

          val intent = Intent(applicationContext, Velden::class.java)
          startActivity(intent)
        }

        backbutton.setOnClickListener{
            val intent = Intent(applicationContext, Velden::class.java)
            startActivity(intent)
        }

    }


}