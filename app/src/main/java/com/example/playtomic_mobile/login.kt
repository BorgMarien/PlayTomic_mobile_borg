package com.example.playtomic_mobile

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlin.math.log

class login : Activity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)
        val db = Firebase.firestore

        val usernameInput = findViewById<View>(R.id.loginName) as TextView
        val passwordInput = findViewById<View>(R.id.loginPassword) as TextView
        val login = findViewById<View>(R.id.login) as Button
        val error = findViewById<View>(R.id.error) as TextView


        login.setOnClickListener{
            db.collection("Persons").whereEqualTo("firstName", usernameInput.text.toString())
                .get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                         if(document.data?.get("password").toString() == passwordInput.text.toString()){
                             val intent = Intent(applicationContext, Velden::class.java)
                             intent.putExtra("ID", document.id)
                             startActivity(intent)
                         }else{
                             error.setText("Mismatch in username and password")
                         }
                    }
                }
        }


    }
}