package com.example.playtomic_mobile

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CalendarView
import android.widget.Spinner
import android.widget.TextView
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import java.util.Arrays

class CreateReservation: Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val extras = intent.extras
        super.onCreate(savedInstanceState)
        setContentView(R.layout.reservefield)

        val ProfileData = Person("null","null","null","null","null","null","null",false);
        val LoginID= "70rvGuMShC9KAPgToNOf"

        //nav
        val backbutton = findViewById<View>(R.id.back) as TextView

        //fields
        val date = findViewById<View>(R.id.dateInput) as CalendarView
        var stringdate = "";

        var availableTimes = ArrayList<String>(Arrays.asList(*resources.getStringArray(R.array.available)))

        val time = findViewById<View>(R.id.time) as Spinner
        val adapter = ArrayAdapter.createFromResource(this,R.array.available, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        time.adapter = adapter;


        val createbutton = findViewById<View>(R.id.CreateButton) as Button

        val db = Firebase.firestore
        val field= Field(extras?.getString("id").toString(), extras?.getString("Name").toString(),extras?.getString("address").toString())

        //get person by id
        val docref = db.collection("Persons").document(LoginID)
        docref.get()
            .addOnSuccessListener { document ->

                if (document != null) {
                    ProfileData.ID= document.id;
                    ProfileData.FirstName= document.data?.get("firstName").toString();
                    ProfileData.LastName= document.data?.get("lastName").toString();
                    ProfileData.HomePlayAddress= document.data?.get("homePlayAddress").toString();
                    ProfileData.MatchType= document.data?.get("matchType").toString();
                    ProfileData.PreferedPlayTime= document.data?.get("preferedPlayTime").toString();
                    ProfileData.CourtPosition= document.data?.get("courtPosition").toString();
                    ProfileData.IsRightHanded=document.data?.get("isRightHanded").toString().toBoolean();
                }
            }


        fun UpdateTimeArray(date:String){
            availableTimes = ArrayList<String>(Arrays.asList(*resources.getStringArray(R.array.available)))
          db.collection("Reservation").whereEqualTo("date", date)
              .get()
              .addOnSuccessListener { documents ->
                  for (document in documents) {
                      if(document.data.get("fieldName").toString() == field.Name){
                          //deze tijd verwijderen uit array
                            val index = availableTimes.indexOf(document.data.get("time").toString());
                          if(index > 0 ){
                              availableTimes.removeAt(index);



                          }


                      }
                  }
              }
            val adp: ArrayAdapter<String> =
                ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, availableTimes)
            time.adapter = adp;


        }

        //get value
        date
            .setOnDateChangeListener(
                CalendarView.OnDateChangeListener { view, year, month, dayOfMonth ->
                   stringdate = (dayOfMonth.toString() + "-"
                            + (month + 1) + "-" + year)
                    UpdateTimeArray(stringdate);

                })


        createbutton.setOnClickListener{

          val reservation = Reservation(stringdate,time.selectedItem.toString(),field.Name,field.Address, ProfileData.ID);
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