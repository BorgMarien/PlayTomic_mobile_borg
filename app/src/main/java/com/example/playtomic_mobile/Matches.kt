package com.example.playtomic_mobile

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.TextView
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class Matches: Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.matches_layout)
        val db = Firebase.firestore

        val LoginID = "70rvGuMShC9KAPgToNOf"
        var logedInUser = Person("null","null","null","null","null","null","null",false);


        //getdata by id
        val docref = db.collection("Persons").document(LoginID)
        docref.get()
            .addOnSuccessListener { document ->

                if (document != null) {
                    logedInUser = Person(LoginID,document.data?.get("firstName").toString(),document.data?.get("lastName").toString(),
                        document.data?.get("homePlayAddress").toString(),document.data?.get("matchType").toString(),document.data?.get("preferedPlayTime").toString(),
                        document.data?.get("courtPosition").toString(),document.data?.get("isRightHanded").toString().toBoolean());
                }
            }

        //nav inputs
        val profiel = findViewById<View>(R.id.nav_profiel) as TextView
        val velden = findViewById<View>(R.id.nav_veld) as TextView
        val matchen = findViewById<View>(R.id.nav_match) as TextView
        val GoToCreateMatch = findViewById<View>(R.id.CreateMatch) as TextView

        //fields
        val matchesListView = findViewById<View>(R.id.matchesList) as ListView

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

        GoToCreateMatch.setOnClickListener{
            val intent = Intent(applicationContext, CreateMatch::class.java)
            startActivity(intent)
        }



        //get variables
        val Matches = arrayListOf<Match>();
        val Ids = arrayListOf<String>();


        //getALLdata
        db.collection("Matches")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                        //matchname.text.toString(),clubs.selectedItem.toString(),stringdate,time.selectedItem.toString(),MatchType.isChecked,PlayerList
                        val tempMatch = Match(document.data?.get("name").toString(),document.data?.get("fieldName").toString(), document.data?.get("date").toString(), document.data?.get("time").toString(), document.data?.get("friendly").toString().toBoolean(), document.data?.get("creatorName").toString() ,document.data?.get("player2").toString(),document.data?.get("player3").toString() ,document.data?.get("player4").toString());
                        Matches.add(tempMatch)
                        Ids.add(document.id)
                        Log.d(ContentValues.TAG, "${document.data}")


                }
                if(Matches.isNotEmpty()){


                    val adapter =  MatchAdapter(applicationContext, 0 , Matches);
                    matchesListView.adapter= adapter;


                    matchesListView.setOnItemClickListener { parent, view, position, id ->
                        val element = adapter.getItem(position) // The item that was clicked
                        if(element is Match){
                            //leave if joined
                            if(element.player2 == logedInUser.FirstName){
                                element.player2 = "";
                            }else if(element.player3 == logedInUser.FirstName){
                                element.player3 = "";
                            }
                            else if(element.player4 == logedInUser.FirstName){
                                element.player4 = "";
                            }
                            //join if not joined
                            else if(element.player2 == ""){
                                element.player2 = logedInUser.FirstName;
                            }else if(element.player3 ==""){
                                element.player3 = logedInUser.FirstName;
                            }else if(element.player4 ==""){
                                element.player4 = logedInUser.FirstName;
                            }
                            db.collection("Matches").document(Ids.get(position)).set(element);
                            recreate();
                        }


                    }
                }
            }

        
    }

}
