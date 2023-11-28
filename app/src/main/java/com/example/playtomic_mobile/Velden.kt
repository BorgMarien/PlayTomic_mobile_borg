package com.example.playtomic_mobile

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore


class Velden: Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.velden_layout)

        //get variables
        val Fields = arrayListOf<Field>();
        val FieldData = Field("Dumme address","Dummy name ")

        //nav inputs
        val profiel = findViewById<View>(R.id.nav_profiel) as TextView
        val velden = findViewById<View>(R.id.nav_veld) as TextView
        val matchen = findViewById<View>(R.id.nav_match) as TextView


        val button = findViewById<View>(R.id.navigationTest) as ImageView

        //firebase
        val db = Firebase.firestore

        //set all values
        fun setFieldData(){
            //manier zoeken om array te tonen op scherm

        }

        //getdata by id
        //getALLdata
        db.collection("Fields")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    FieldData.Address = document.data.get("Address").toString();
                    FieldData.Name = document.data.get("Name").toString();
                    Fields.add(FieldData)
                }
                setFieldData();
            }






        button.setOnClickListener{
            val intent = Intent(applicationContext, CreateMatch::class.java)
            startActivity(intent)
        }

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
    }
}