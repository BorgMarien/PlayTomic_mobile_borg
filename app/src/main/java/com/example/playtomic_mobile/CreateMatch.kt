package com.example.playtomic_mobile

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable.Creator
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CalendarView
import android.widget.CheckBox
import android.widget.Spinner
import android.widget.TextView
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import java.util.Arrays

class CreateMatch: Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val extras = intent.extras
        val db = Firebase.firestore
        super.onCreate(savedInstanceState)
        setContentView(R.layout.creatematch)
        var availableTimes = ArrayList<String>(Arrays.asList(*resources.getStringArray(R.array.available)))

        val LoginID = extras?.getString("ID").toString()
        var Creator = Person("null","null","null","null","null","null","null",false,"","");


        //getdata by id
        val docref = db.collection("Persons").document(LoginID)
        docref.get()
            .addOnSuccessListener { document ->

                if (document != null) {
                    Creator = Person(LoginID,document.data?.get("firstName").toString(),document.data?.get("lastName").toString(),
                        document.data?.get("homePlayAddress").toString(),document.data?.get("matchType").toString(),document.data?.get("preferedPlayTime").toString(),
                                document.data?.get("courtPosition").toString(),document.data?.get("isRightHanded").toString().toBoolean(),document.data?.get("passWord").toString(),document.data?.get("image").toString());
                }
            }



        //nav
        val back = findViewById<View>(R.id.backButton) as TextView
        back.setOnClickListener{
            val intent = Intent(applicationContext, Matches::class.java)
            intent.putExtra("ID", extras?.getString("ID"))
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


        fun UpdateTimeArray(date:String){
            availableTimes = ArrayList<String>(Arrays.asList(*resources.getStringArray(R.array.available)))
            db.collection("Reservation").whereEqualTo("date", date)
                .get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        if(document.data.get("fieldName").toString() == clubs.selectedItem.toString()){
                            //deze tijd verwijderen uit array
                            val index = availableTimes.indexOf(document.data.get("time").toString());

                            if(index >= 0 ){
                                availableTimes.removeAt(index);
                                Log.d(TAG, "${availableTimes}")
                            }
                        }
                    }
                }
            val adp: ArrayAdapter<String> =
                ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, availableTimes)
            time.adapter = adp;


        }
        val date = findViewById<View>(R.id.MatchDate) as CalendarView
        var stringdate = "";
        //get value
        date
            .setOnDateChangeListener(
                CalendarView.OnDateChangeListener { view, year, month, dayOfMonth ->
                    stringdate = (dayOfMonth.toString() + "-"
                            + (month + 1) + "-" + year)

                    UpdateTimeArray(stringdate);
                })




        val createbutton = findViewById<View>(R.id.CreateMatchButton) as Button
        createbutton.setOnClickListener{
            val Match = Match(matchname.text.toString(),clubs.selectedItem.toString(),stringdate,time.selectedItem.toString(),MatchType.isChecked,Creator.FirstName,"","","",Creator.Image,"","","");
            db.collection("Matches").add(Match)

            //getALLdata
            db.collection("Fields").whereEqualTo("Name", clubs.selectedItem.toString())
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        val reservation = Reservation(stringdate,time.selectedItem.toString(),document.data.get("Address").toString(),clubs.selectedItem.toString(), LoginID);
                        db.collection("Reservation").add(reservation)
                    }

                }


            val intent = Intent(applicationContext, Matches::class.java)
            intent.putExtra("ID", extras?.getString("ID"))
            startActivity(intent)
        }
    }
}