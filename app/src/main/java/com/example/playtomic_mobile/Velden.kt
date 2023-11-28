package com.example.playtomic_mobile

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore


class Velden: Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.velden_layout)

        //get variables
        val Fields = arrayListOf<Field>();


        //nav inputs
        val profiel = findViewById<View>(R.id.nav_profiel) as TextView
        val velden = findViewById<View>(R.id.nav_veld) as TextView
        val matchen = findViewById<View>(R.id.nav_match) as TextView

        //fields
        val fieldOnename = findViewById<View>(R.id.fieldName1) as TextView
        val fieldTwoname = findViewById<View>(R.id.fieldName2) as TextView
        val fieldTreename = findViewById<View>(R.id.fieldName3) as TextView

        val FieldOneAddress = findViewById<View>(R.id.fieldAddress) as TextView
        val FieldTwoAddress = findViewById<View>(R.id.fieldAddress2) as TextView
        val FieldTreeAddress = findViewById<View>(R.id.fieldAddress3) as TextView


        //firebase
        val db = Firebase.firestore

        //set all values
        fun setFieldData(){

            Log.d(TAG, "DocumentSnapshot added with ID: ${Fields[0]}")
            if (Fields.size > 0){
                fieldOnename.text= Fields[0].Name
                FieldOneAddress.text= Fields[0].Address

                fieldTwoname.text= Fields[1].Name
                FieldTwoAddress.text= Fields[1].Address

                fieldTreename.text= Fields[2].Name
                FieldTreeAddress.text= Fields[2].Address
            }

        }

        //getdata by id
        //getALLdata
        db.collection("Fields")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                   Fields.add(Field(document.data.get("Address").toString(),document.data.get("Name").toString()))
                }
                setFieldData();

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


