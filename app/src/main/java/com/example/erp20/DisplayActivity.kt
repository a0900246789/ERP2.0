package com.example.erp20

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class DisplayActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display)
        val passedData: String =intent.getStringExtra("data").toString()
        val theTextView = findViewById<TextView>(R.id.TextName)
        theTextView.text = passedData
    }
}