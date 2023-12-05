package com.example.playtomic_mobile

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ListView
import android.widget.TextView
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class MyReservations: Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val extras = intent.extras
        super.onCreate(savedInstanceState)
        setContentView(R.layout.myreservations)
        val LoginID= "70rvGuMShC9KAPgToNOf"

        //fields
        val reservationListview = findViewById<View>(R.id.reservationsList) as ListView

        //nav
        val back =  findViewById<View>(R.id.backtoFields) as TextView

        back.setOnClickListener{
            val intent = Intent(applicationContext, Velden::class.java)
            startActivity(intent)
        }

        //get variables
        val Reservations = arrayListOf<Reservation>();

        //firebase
        val db = Firebase.firestore
        //getALLdata
        db.collection("Reservation")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    if(document.data?.get("playerID") ==LoginID ){
                        val tempReservation = Reservation(document.data?.get("date").toString(),document.data?.get("time").toString(),document.data?.get("fieldAdress").toString(),document.data?.get("fieldName").toString(),document.data?.get("playerID").toString() )
                        Reservations.add(tempReservation)
                        Log.d(TAG, "${tempReservation}")

                    }
                }
                if(Reservations.isNotEmpty()){
                    //string to date
                    //val localDate = LocalDate.parse("01-06-2022", DateTimeFormatter.ofPattern("MM-dd-yyyy"))
                    val adapter =  ReservationAdapter(applicationContext, 0 , Reservations);
                    reservationListview.adapter= adapter;
                }
            }
        }


}