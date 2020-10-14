package com.example.tp1kotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        DataController(this).recupererData()
    }

    fun onRecupDone(listeCocktails: ListeCocktails){
        val intent=Intent(this,PresentationActivity::class.java)
        intent.putExtra("listeCocktails",listeCocktails)
        startActivity(intent)
        finish()
    }
}