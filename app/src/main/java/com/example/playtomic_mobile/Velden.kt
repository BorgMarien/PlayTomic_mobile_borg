package com.example.playtomic_mobile

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore


class Velden: Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.velden_layout)
        val extras = intent.extras
        //get variables
        val Fields = arrayListOf<Field>();


        //nav inputs
        val profiel = findViewById<View>(R.id.nav_profiel) as TextView
        val velden = findViewById<View>(R.id.nav_veld) as TextView
        val matchen = findViewById<View>(R.id.nav_match) as TextView
        val imagenav1 = findViewById<View>(R.id.imagenav1) as ImageView
        val imagenav2 = findViewById<View>(R.id.imagenav2) as ImageView
        val imagenav3 = findViewById<View>(R.id.imagenav3) as ImageView
        val viewReservations = findViewById<View>(R.id.myreservations) as TextView

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
            if (Fields.size > 0){
                fieldOnename.text= Fields[0].Name
                FieldOneAddress.text= Fields[0].Address

                fieldTwoname.text= Fields[1].Name
                FieldTwoAddress.text= Fields[1].Address

                fieldTreename.text= Fields[2].Name
                FieldTreeAddress.text= Fields[2].Address
            }
        }

        //getALLdata
        db.collection("Fields")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                   Fields.add(Field(document.id,document.data.get("Address").toString(),document.data.get("Name").toString()))
                }
                setFieldData();

            }

        profiel.setOnClickListener{
            val intent = Intent(applicationContext, Profile::class.java)
            intent.putExtra("ID", extras?.getString("ID"))
            startActivity(intent)
        }

        velden.setOnClickListener{
            val intent = Intent(applicationContext, Velden::class.java)
            intent.putExtra("ID", extras?.getString("ID"))
            startActivity(intent)
        }

        matchen.setOnClickListener{
            val intent = Intent(applicationContext, Matches::class.java)
            intent.putExtra("ID", extras?.getString("ID"))
            startActivity(intent)
        }

        imagenav1.setOnClickListener{
            val intent = Intent(applicationContext, CreateReservation::class.java)
            intent.putExtra("id", Fields[0].id)
            intent.putExtra("Name", Fields[0].Name)
            intent.putExtra("address", Fields[0].Address)
            intent.putExtra("ID", extras?.getString("ID"))
            startActivity(intent)
        }
        imagenav2.setOnClickListener{
            val intent = Intent(applicationContext, CreateReservation::class.java)
           intent.putExtra("id", Fields[1].id)
            intent.putExtra("Name", Fields[1].Name)
        intent.putExtra("address", Fields[1].Address)
            intent.putExtra("ID", extras?.getString("ID"))
            startActivity(intent)
        }
        imagenav3.setOnClickListener{
            val intent = Intent(applicationContext, CreateReservation::class.java)
            intent.putExtra("id", Fields[2].id)
            intent.putExtra("Name", Fields[2].Name)
            intent.putExtra("address", Fields[2].Address)
            intent.putExtra("ID", extras?.getString("ID"))
            startActivity(intent)

        }

        viewReservations.setOnClickListener{
            val intent = Intent(applicationContext, MyReservations::class.java)
            intent.putExtra("ID", extras?.getString("ID"))
            startActivity(intent)

        }
    }
}


