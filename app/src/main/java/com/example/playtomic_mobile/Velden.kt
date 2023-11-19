package com.example.playtomic_mobile

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView


class Velden: Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.velden_layout)

        //nav inputs
        val profiel = findViewById<View>(R.id.nav_profiel) as TextView
        val velden = findViewById<View>(R.id.nav_veld) as TextView
        val matchen = findViewById<View>(R.id.nav_match) as TextView


        val button = findViewById<View>(R.id.navigationTest) as ImageView



        button.setOnClickListener{
            val intent = Intent(applicationContext, CreateMatch::class.java)
            startActivity(intent)
        }



        profiel.setOnClickListener{
            val intent = Intent(applicationContext, CreateMatch::class.java)
            startActivity(intent)
        }

        velden.setOnClickListener{
            val intent = Intent(applicationContext, velden::class.java)
            startActivity(intent)
        }

        matchen.setOnClickListener{
            val intent = Intent(applicationContext, matchen::class.java)
            startActivity(intent)
        }
    }
}