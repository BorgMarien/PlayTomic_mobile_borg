package com.example.playtomic_mobile

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView


class Velden: Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.velden_layout)

        val button = findViewById<View>(R.id.navigationTest) as ImageView

        button.setOnClickListener{
            val intent = Intent(applicationContext, CreateMatch::class.java)
            startActivity(intent)
        }
    }
}